package com.example.sample.trans.aliyun;

import com.alibaba.nls.client.AccessToken;
import com.alibaba.nls.client.protocol.InputFormatEnum;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriber;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberResponse;
import com.example.sample.util.ErrorPrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AliyunSpeechTranscriber {

    private static final Logger logger = LoggerFactory.getLogger(AliyunSpeechTranscriber.class);

    private String appKey;
    private NlsClient client;

    private AliyunSpeechTranscriber(String appKey, String id, String secret, String url) {
        this.appKey = appKey;
        // 创建 NlsClient 实例，应用全局创建一个即可，生命周期可和整个应用保持一致，默认服务地址为阿里云线上服务地址
        // 这里简单演示了获取 token 的代码，该 token 会过期，实际使用时注意在 accessToken.getExpireTime() 过期前再次获取 token
        AccessToken accessToken = new AccessToken(id, secret);
        try {
            accessToken.apply();
            String token = accessToken.getToken();
            long now = System.currentTimeMillis() / 1000;
            logger.info(">>>>> TOKEN: [{}], EXPIRE_TIME: [{}]", token, accessToken.getExpireTime());
            logger.info(">>>>> TOKEN VALID_DURATION: {}", accessToken.getExpireTime() - now);
            // 创建 NlsClient 实例,应用全局创建一个即可，用户指定服务地址
            if (url.isEmpty()) {
                client = new NlsClient(id, secret);
            } else {
                client = new NlsClient(url, token);
            }
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
        }
    }

    private static SpeechTranscriberListener getTranscriberListener() {
        return new SpeechTranscriberListener() {
            // 识别出中间结果。服务端识别出一个字或词时会返回此消息。仅当 setEnableIntermediateResult(true) 时，才会有此类消息返回
            @Override
            public void onTranscriptionResultChange(SpeechTranscriberResponse response) {
                logger.info(">>>>> ON_RESULT_CHANGE, TASK_ID: {}, NAME: {}, STATUS: {}, INDEX: {}, RESULT: {}, TIME: {}",
                        response.getTaskId(),
                        response.getName(),
                        // 状态码 20000000 表示正常识别
                        response.getStatus(),
                        // 句子编号，从1开始递增
                        response.getTransSentenceIndex(),
                        // 当前的识别结果
                        response.getTransSentenceText(),
                        // 当前已处理的音频时长，单位是毫秒
                        response.getTransSentenceTime()
                );
            }

            // 服务开始
            @Override
            public void onTranscriberStart(SpeechTranscriberResponse response) {
                // task_id 很重要，是调用方和服务端通信的唯一 ID 标识，当遇到问题时，需要提供此 task_id 以便排查
                logger.info(">>>>> ON_START, task_id: " + response.getTaskId() + ", name: " + response.getName() + ", status: " + response.getStatus());
            }

            // 识别语句开始
            @Override
            public void onSentenceBegin(SpeechTranscriberResponse response) {
                logger.info(">>>>> ON_BEGIN, TASK_ID: {}, NAME: {}, STATUS: {}", response.getTaskId(), response.getName(), response.getStatus());
            }

            // 识别出一句话。服务端会智能断句，当识别到一句话结束时会返回此消息
            @Override
            public void onSentenceEnd(SpeechTranscriberResponse response) {
                logger.info(">>>>> ON_END, TASK_ID: {}, NAME: {}, STATUS:{}, INDEX: {}, RESULT: {}, CONFIDENCE: {}, BEGIN_TIME: {}, TIME: {}",
                        response.getTaskId(),
                        response.getName(),
                        // 状态码 20000000 表示正常识别
                        response.getStatus(),
                        // 句子编号，从1开始递增
                        response.getTransSentenceIndex(),
                        // 当前的识别结果
                        response.getTransSentenceText(),
                        // 置信度
                        response.getConfidence(),
                        // 开始时间
                        response.getSentenceBeginTime(),
                        // 当前已处理的音频时长，单位是毫秒
                        response.getTransSentenceTime()
                );
            }

            // 服务完毕
            @Override
            public void onTranscriptionComplete(SpeechTranscriberResponse response) {
                logger.info(">>>>> ON_COMPLETE, TASK_ID: {}, NAME: {}, STATUS: {}", response.getTaskId(), response.getName(), response.getStatus());
            }

            // 识别失败
            @Override
            public void onFail(SpeechTranscriberResponse response) {
                //  task_id 很重要，是调用方和服务端通信的唯一ID标识，当遇到问题时，需要提供此 task_id 以便排查
                logger.info(">>>>> ON_FAIL, TASK_ID: {}, STATUS: {}, STATUS_TEXT: {}",
                        response.getTaskId(), response.getStatus(), response.getStatusText());
            }
        };
    }

    /// 根据二进制数据大小计算对应的同等语音长度
    /// sampleRate 仅支持 8000 或 16000
    private static int getSleepDelta(int dataSize, int sampleRate) {
        // 仅支持 16 位采样
        int sampleBytes = 16;
        // 仅支持单通道
        int soundChannel = 1;
        return (dataSize * 10 * 8000) / (160 * sampleRate);
    }

    private void process(String filepath) {
        SpeechTranscriber transcriber = null;
        try {
            // 创建实例,建立连接
            transcriber = new SpeechTranscriber(client, getTranscriberListener());
            transcriber.setAppKey(appKey);
            // 输入音频编码方式
            transcriber.setFormat(InputFormatEnum.PCM);
            // 输入音频采样率
            transcriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
            // 是否返回中间识别结果
            transcriber.setEnableIntermediateResult(false);
            // 是否生成并返回标点符号
            transcriber.setEnablePunctuation(true);
            // 是否将返回结果规整化,比如将一百返回为100
            transcriber.setEnableITN(false);
            // 此方法将以上参数设置序列化为json发送给服务端,并等待服务端确认
            transcriber.start();
            File file = new File(filepath);
            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[3200];
            // int len;
            // while ((len = fis.read(buf)) != -1) {
            while (fis.read(buf) != -1) {
                // 以下日志输出用于调试
                // logger.info("send data pack length: " + len);
                transcriber.send(buf);
                // 这里是用读取本地文件的形式模拟实时获取语音流并发送的，因为read很快，所以这里需要sleep
                // 如果是真正的实时获取语音，则无需sleep, 如果是8k采样率语音，第二个参数改为8000
                // int deltaSleep = getSleepDelta(len, 16000);
                // Thread.sleep(deltaSleep);
            }
            // 通知服务端语音数据发送完毕,等待服务端处理完成
            long now = System.currentTimeMillis();
            logger.info(">>>>> ASR WAIT FOR COMPLETE");
            transcriber.stop();
            logger.info("ASR LATENCY: {} MS", System.currentTimeMillis() - now);
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
        } finally {
            if (null != transcriber) {
                transcriber.close();
            }
        }
    }

    private void shutdown() {
        client.shutdown();
    }

    public static void main(String[] args) {
        String appKey = "";
        String id = "";
        String secret = "";
        // 默认即可，默认值：wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1
        String url = "wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1";
        // 这里用一个本地文件来模拟发送实时流数据，实际使用时，用户可以从某处实时采集或接收语音流并发送到 ASR 服务端
        String filepath = "D:\\download\\nls-sample-16k.wav";
        AliyunSpeechTranscriber aliyunTranscriber = new AliyunSpeechTranscriber(appKey, id, secret, url);
        aliyunTranscriber.process(filepath);
        aliyunTranscriber.shutdown();
    }

}

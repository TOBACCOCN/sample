package com.example.sample.trans.aliyun;

import com.alibaba.nls.client.AccessToken;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.OutputFormatEnum;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizer;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerListener;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerResponse;
import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@Slf4j
public class AliyunSpeechSynthesizer {

    // private static final Logger logger = LoggerFactory.getLogger(AliyunSpeechSynthesizer.class);
    private static long startTime;
    private String appKey;
    private NlsClient client;

    private AliyunSpeechSynthesizer(String appKey, String accessKeyId, String accessKeySecret, String url) {
        this.appKey = appKey;
        // 创建 NlsClient 实例，应用全局创建一个即可，生命周期可和整个应用保持一致，默认服务地址为阿里云线上服务地址
        // 这里简单演示了获取 token 的代码，该 token 会过期，实际使用时注意在 accessToken.getExpireTime() 过期前再次获取 token
        AccessToken accessToken = new AccessToken(accessKeyId, accessKeySecret);
        try {
            accessToken.apply();
            String token = accessToken.getToken();
            long now = System.currentTimeMillis() / 1000;
            log.info(">>>>> TOKEN: [{}], EXPIRE_TIME: [{}]", token, accessToken.getExpireTime());
            log.info(">>>>> TOKEN VALID_DURATION: {}", accessToken.getExpireTime() - now);
            // 创建 NlsClient 实例,应用全局创建一个即可，用户指定服务地址
            if (url.isEmpty()) {
                client = new NlsClient(accessToken.getToken());
            } else {
                client = new NlsClient(url, accessToken.getToken());
            }
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
    }

    private static SpeechSynthesizerListener getSynthesizerListener() {
        SpeechSynthesizerListener listener = null;
        try {
            listener = new SpeechSynthesizerListener() {
                File file = new File("tts_test.wav");
                FileOutputStream fos = new FileOutputStream(file);
                private boolean firstRecvBinary = true;

                //语音合成结束
                @Override
                public void onComplete(SpeechSynthesizerResponse response) {
                    // 当 onComplete 时表示所有TTS数据已经接收完成，因此这个是整个合成延迟，该延迟可能较大，未必满足实时场景
                    log.info(">>>>> ON_COMPLETE, NAME: {}, status: {}, output_FILE: {}",
                            response.getName(), response.getStatus(), file.getAbsolutePath());
                }

                // 语音合成的语音二进制数据
                @Override
                public void onMessage(ByteBuffer message) {
                    try {
                        if (firstRecvBinary) {
                            // 此处是计算首包语音流的延迟，收到第一包语音流时，即可以进行语音播放，以提升响应速度（特别是实时交互场景下）
                            firstRecvBinary = false;
                            long now = System.currentTimeMillis();
                            log.info(">>>>> TTS FIRST LATENCY: [{}] MS", now - AliyunSpeechSynthesizer.startTime);
                        }
                        byte[] bytesArray = new byte[message.remaining()];
                        message.get(bytesArray, 0, bytesArray.length);
                        fos.write(bytesArray);
                    } catch (IOException e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                }

                @Override
                public void onFail(SpeechSynthesizerResponse response) {
                    // task_id 很重要，是调用方和服务端通信的唯一 ID 标识，当遇到问题时，需要提供此 task_id 以便排查
                    log.info(">>>>> ON_FAIL, TASK_ID: {}, STATUS: {}, STATUS_TEXT: {}", response.getTaskId(),
                            // 状态码 20000000 表示识别成功
                            response.getStatus(),
                            // 错误信息
                            response.getStatusText());
                }
            };
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
        return listener;
    }

    private void process() {
        SpeechSynthesizer synthesizer = null;
        try {
            //创建实例,建立连接
            synthesizer = new SpeechSynthesizer(client, getSynthesizerListener());
            synthesizer.setAppKey(appKey);
            // 设置返回音频的编码格式
            synthesizer.setFormat(OutputFormatEnum.WAV);
            // 设置返回音频的采样率
            synthesizer.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
            // 发音人
            synthesizer.setVoice("siyue");
            // 语调，范围是-500~500，可选，默认是 0
            synthesizer.setPitchRate(100);
            // 语速，范围是-500~500，默认是 0
            synthesizer.setSpeechRate(100);
            // 设置用于语音合成的文本
            synthesizer.setText("欢迎使用阿里云智能语音合成服务，您可以说北京明天天气怎么样啊");
            // 此方法将以上参数设置序列化为 json 发送给服务端,并等待服务端确认
            long start = System.currentTimeMillis();
            synthesizer.start();
            log.info(">>>>> TTS START LATENCY: [{}] MS", System.currentTimeMillis() - start);
            AliyunSpeechSynthesizer.startTime = System.currentTimeMillis();
            // 等待语音合成结束
            synthesizer.waitForComplete();
            log.info(">>>>> TTS STOP LATENCY: [{}] MS", System.currentTimeMillis() - start);
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        } finally {
            // 关闭连接
            if (null != synthesizer) {
                synthesizer.close();
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
        AliyunSpeechSynthesizer aliyunSynthesizer = new AliyunSpeechSynthesizer(appKey, id, secret, url);
        aliyunSynthesizer.process();
        aliyunSynthesizer.shutdown();
    }

}

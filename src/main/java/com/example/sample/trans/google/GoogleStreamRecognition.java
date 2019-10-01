/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sample.trans.google;

import com.example.sample.util.ErrorPrintUtil;
import com.example.sample.util.IOUtil;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1p1beta1.*;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GoogleStreamRecognition {

    // private static Logger logger = LoggerFactory.getLogger(GoogleStreamRecognition.class);

    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";

    public static void main(String... args) throws Exception {
        ClientStream<StreamingRecognizeRequest> clientStream = initClient();
        String langCode = "zh";
        sendConfigData(clientStream, langCode);

        String filePath;
        if (System.getProperty("os.name").toLowerCase().contains("window")) {
            filePath = "D:\\download\\中文英文语音数据\\中文30小时\\wav\\train\\S0002\\BAC009S0002W0123.wav";
        } else {
            filePath = "/home/hans/zyh/BAC009S0002W0123.wav";
        }
        InputStream inputStream = new ByteArrayInputStream(IOUtil.file2Bytes(filePath));
        // buffer size in bytes
        // int BYTES_PER_BUFFER = 6400;
        int size = 1280;
        byte[] buffer = new byte[size];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            sendAudioData(clientStream, Arrays.copyOf(buffer, len));
        }
        clientStream.closeSend();
        Thread.currentThread().join();
    }

    /**
     * 获取流式语音识别客户端对象
     *
     * @return 流式语音识别客户端对象
     */
    private static ClientStream<StreamingRecognizeRequest> initClient() throws IOException {
        // 构建语音识别客户端对象
        SpeechClient client = SpeechClient.create();
        // 构建语音识别响应回调对象
        ResponseObserver<StreamingRecognizeResponse> responseObserver = new SimpleResponseObserver();
        // 构建流式语音识别客户端对象
        return client.streamingRecognizeCallable().splitCall(responseObserver);
    }

    private static void sendConfigData(ClientStream<StreamingRecognizeRequest> clientStream,
                                       String languageCode) {
        // 构建音频元数据配置对象
        RecognitionConfig recognitionConfig =
                RecognitionConfig.newBuilder()
                        .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                        .setLanguageCode(languageCode)
                        .setSampleRateHertz(16000)
                        .build();
        // 构建音频元数据的流式识别配置对象
        StreamingRecognitionConfig streamingRecognitionConfig =
                StreamingRecognitionConfig.newBuilder()
                        .setConfig(recognitionConfig)
                        .setInterimResults(true)
                        .build();
        // The first request in a streaming call has to be a config
        // 构建包含配置音频元数据等信息的请求对象
        StreamingRecognizeRequest request =
                StreamingRecognizeRequest.newBuilder()
                        .setStreamingConfig(streamingRecognitionConfig)
                        .build();
        // 发送音频元数据等信息
        log.info(">>>>> SENDING CONFIG_DATA: {}", request);
        clientStream.send(request);
    }

    /**
     * 发送音频数据
     *
     * @param clientStream 流式语音识别客户端对象
     * @param bytes        音频二进制数据部分字节数组
     */
    private static void sendAudioData(ClientStream<StreamingRecognizeRequest> clientStream, byte[] bytes) {
        // 发送音频数据
        clientStream.send(StreamingRecognizeRequest
                .newBuilder()
                .setAudioContent(ByteString.copyFrom(bytes))
                .build());
    }

    private static class SimpleResponseObserver implements ResponseObserver<StreamingRecognizeResponse> {

        @Override
        public void onStart(StreamController streamController) {
            log.info(">>>>> ON_START");
        }

        @Override
        public void onResponse(StreamingRecognizeResponse response) {
            List<StreamingRecognitionResult> resultsList = response.getResultsList();
            if (resultsList != null && resultsList.size() > 0) {
                StreamingRecognitionResult result = resultsList.get(0);
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                if (result.getIsFinal()) {
                    log.info(">>>>> RESULT: {}", alternative.getTranscript());
                }
            }
        }

        @Override
        public void onError(Throwable throwable) {
            log.info(">>>>> ON_ERROR");
            ErrorPrintUtil.printErrorMsg(log, throwable);
        }

        @Override
        public void onComplete() {
            log.info(">>>>> ON_COMPLETE");
        }
    }

}

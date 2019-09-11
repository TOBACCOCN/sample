package com.example.sample.trans.microsoft;

import com.example.sample.util.ErrorPrintUtil;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.audio.AudioInputStream;
import com.microsoft.cognitiveservices.speech.audio.PushAudioInputStream;
import com.microsoft.cognitiveservices.speech.translation.SpeechTranslationConfig;
import com.microsoft.cognitiveservices.speech.translation.TranslationRecognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Semaphore;

// https://github.com/Azure-Samples/cognitive-services-speech-sdk/tree/master/samples/java/jre/console
public class MicrosoftStreamRecognition {

    private static Logger logger = LoggerFactory.getLogger(MicrosoftStreamRecognition.class);

    public static void main(String[] args) throws Exception {
        String language = "zh-CN";
        PushAudioInputStream audioInput = initSpeechRecognizer(language);

        InputStream inputStream =
                new FileInputStream("D:\\download\\中文英文语音数据\\中文30小时\\wav\\train\\S0002\\BAC009S0002W0123.wav");
        byte[] buf = new byte[4096];

        while (inputStream.read(buf) != -1) {
            audioInput.write(buf);
        }

        audioInput.close();
        inputStream.close();
        // String from = "zh-CN";
        // String to = "en";
        // byte[] audioBytes = Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\16k_10.pcm"));
        // speechTrans(audioBytes, from, to);
    }

    private static PushAudioInputStream initSpeechRecognizer(String language) {
        // Creates an instance of a speech config with specified
        // 创建语音识别配置实例
        String subscriptionKey = "";
        String region = "westus";
        SpeechConfig config = SpeechConfig.fromSubscription(subscriptionKey, region);
        // 设置识别语言
        config.setSpeechRecognitionLanguage(language);

        // Create the push stream to push audio to.
        // 创建语音识别的推送流作为语音识别配置项
        PushAudioInputStream pushStream = AudioInputStream.createPushStream();
        AudioConfig audioInput = AudioConfig.fromStreamInput(pushStream);

        // Creates a speech recognizer using Push Stream as audio input.
        // 创建语音识别对象实例
        SpeechRecognizer recognizer = new SpeechRecognizer(config, audioInput);

        // Subscribes to events. 注册事件
        addEventListener(recognizer, null);

        try {
            // Starts continuous recognition. Uses stopContinuousRecognitionAsync() to stop recognition.
            // 异步获取识别结果
            recognizer.startContinuousRecognitionAsync().get();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
        }
        return pushStream;
    }

    private static void speechTrans(byte[] audioBytes, String from, String to) throws Exception {
        Semaphore stopTranslationWithAudioStreamSemaphore = new Semaphore(0);

        // 创建语音识别配置实例
        String subscriptionKey = "";
        String region = "westus";
        SpeechTranslationConfig config =
                SpeechTranslationConfig.fromSubscription(subscriptionKey, region);

        // Sets source and target languages 设置源语言目标语言
        config.setSpeechRecognitionLanguage(from);
        config.addTargetLanguage(to);

        // Create an audio stream from a wav file.
        // PullAudioInputStreamCallback callback =
        //         new WavStream(new FileInputStream("D:\\download\\中文英文语音数据\\中文30小时\\wav\\train\\S0002\\BAC009S0002W0123.wav"));
        WavStream callback = new WavStream(new ByteArrayInputStream(audioBytes));
        AudioConfig audioInput = AudioConfig.fromStreamInput(callback);

        // Creates a translation recognizer using audio stream as input.
        // 创建语音识别翻译对象实例
        TranslationRecognizer recognizer = new TranslationRecognizer(config, audioInput);

        // Subscribes to events. 注册事件
        addEventListener(recognizer, stopTranslationWithAudioStreamSemaphore);

        // Starts continuous recognition. Uses StopContinuousRecognitionAsync() to stop recognition.
        // 异步获取识别翻译结果
        recognizer.startContinuousRecognitionAsync().get();

        // Waits for completion. 等待翻译结束
        stopTranslationWithAudioStreamSemaphore.acquire();

        // Stops translation. 停止翻译
        recognizer.stopContinuousRecognitionAsync().get();
    }

    private static void addEventListener(Recognizer recognizer, Semaphore semaphore) {
        if (recognizer instanceof SpeechRecognizer) {
            SpeechRecognizer speechRecognizer = (SpeechRecognizer) recognizer;
            // 识别中
            // 以下日志输出用于调试
            speechRecognizer.recognizing.addEventListener((s, e) ->
                    logger.info(">>>>> RECOGNIZING: {}", e.getResult().getText())
            );

            // 识别完成
            speechRecognizer.recognized.addEventListener((s, e) -> {
                if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                    logger.info(">>>>> RECOGNIZED: {}", e.getResult().getText());
                } else if (e.getResult().getReason() == ResultReason.NoMatch) {
                    logger.info(">>>>> NOMATCH: Speech could not be recognized.");
                }
            });

            // 取消识别
            speechRecognizer.canceled.addEventListener((s, e) -> {
                logger.info(">>>>> CANCELED, REASON: [{}]", e.getReason());

                if (e.getReason() == CancellationReason.Error) {
                    logger.info(">>>>> CANCELED: ERROR_CODE: [{}], ERROR_DETAILS: [{}]",
                            e.getErrorCode(), e.getErrorDetails());
                }
            });
        }

        if (recognizer instanceof TranslationRecognizer) {
            TranslationRecognizer translationRecognizer = (TranslationRecognizer) recognizer;
            // 识别翻译中
            // 以下日志输出用于调试
            translationRecognizer.recognizing.addEventListener((s, e) -> {
                logger.info(">>>>> RECOGNIZING: [{}]", e.getResult().getText());

                Map<String, String> map = e.getResult().getTranslations();
                for (String element : map.keySet()) {
                    logger.info(">>>>> TRANSLATING INTO [{}]: [{}]", element, map.get(element));
                }
            });

            // 识别翻译完成
            translationRecognizer.recognized.addEventListener((s, e) -> {
                if (e.getResult().getReason() == ResultReason.TranslatedSpeech) {
                    logger.info(">>>>> RECOGNIZED: [{}]", e.getResult().getText());

                    Map<String, String> map = e.getResult().getTranslations();
                    for (String element : map.keySet()) {
                        logger.info(">>>>> TRANSLATED INTO [{}]: [{}]", element, map.get(element));
                    }
                }
                if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                    logger.info(">>>>> RECOGNIZED: [{}]", e.getResult().getText());
                } else if (e.getResult().getReason() == ResultReason.NoMatch) {
                    logger.info(">>>>> NOMATCH: SPEECH COULD NOT BE RECOGNIZED.");
                }
            });

            translationRecognizer.canceled.addEventListener((s, e) -> {
                logger.info(">>>>> CANCELED: [{}]", e.getReason());

                if (e.getReason() == CancellationReason.Error) {
                    logger.info(">>>>> CANCELED: ERROR_CODE: [{}], ERROR_DETAILS: [{}]",
                            e.getErrorCode(), e.getErrorDetails());
                }

                semaphore.release();
            });
        }

        // 会话开始
        recognizer.sessionStarted.addEventListener((s, e) -> {
            logger.info(">>>>> SESSION STARTED");
        });

        // 会话结束
        recognizer.sessionStopped.addEventListener((s, e) -> {
            logger.info(">>>>> SESSION STOPPED");
            if (semaphore != null) {
                semaphore.release();
            }
        });
    }

}

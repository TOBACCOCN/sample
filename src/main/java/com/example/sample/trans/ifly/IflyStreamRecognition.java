package com.example.sample.trans.ifly;

import com.example.sample.util.ErrorPrintUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 语音听写流式 WebAPI 接口调用示例 接口文档（必看）：https://doc.xfyun.cn/rest_api/语音听写（流式版）.html
 * webapi 听写服务参考帖子（必看）：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=38947&extra=
 * 语音听写流式WebAPI 服务，热词使用方式：登陆开放平台https://www.xfyun.cn/后，找到控制台--我的应用---语音听写---个性化热词，上传热词
 * 注意：热词只能在识别的时候会增加热词的识别权重，需要注意的是增加相应词条的识别率，但并不是绝对的，具体效果以您测试为准。
 * 错误码链接：https://www.xfyun.cn/document/error-code （code返回错误码时必看）
 * 语音听写流式WebAPI 服务，方言或小语种试用方法：登陆开放平台https://www.xfyun.cn/后，在控制台--语音听写（流式）--方言/语种处添加
 * 添加后会显示该方言/语种的参数值
 */

@Slf4j
public class IflyStreamRecognition {

    // private static Logger logger = LoggerFactory.getLogger(IflyWebsocketListener.class);

    //中英文
    private static final String url = "https://iat-api.xfyun.cn/v2/iat";
    //小语种
    // private static final String url = "https://iat-niche-api.xfyun.cn/v2/iat";
    private static final String appid = "";
    private static final String apiKey = "";
    private static final String apiSecret = "";
    private static String filePath = "C:\\Users\\Administrator\\Desktop\\16k_10.pcm";
    private static final int firstFrameStatus = 0;
    private static final int continueFrameStatus = 1;
    private static final int lastFrameStatus = 2;
    public static final Gson json = new Gson();
    private static Decoder decoder = new Decoder();
    private static long begin = System.currentTimeMillis();

    public static void main(String[] args) throws Exception {
        // // 构建鉴权 url
        // String authUrl = getAuthUrl();
        // OkHttpClient client = new OkHttpClient.Builder().build();
        // //将url中的 schema http:// 和 https:// 分别替换为 ws:// 和 wss://
        // String url = authUrl.replace("https://", "wss://");
        // log.info(">>>>> URL: {}", url);
        //
        // Request request = new Request.Builder().url(url).build();
        // client.newWebSocket(request, new IflyWebsocketListener());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("abcd".getBytes());
        byte[] buf = new byte[2];
        int len;
        while ((len = byteArrayInputStream.read(buf)) != -1) {
            System.out.println(new String(Arrays.copyOf(buf, len)));
        }
    }

    private static String getAuthUrl() throws Exception {
        URL uri = new URL(url);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        Charset charset = StandardCharsets.UTF_8;

        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
        mac.init(spec);
        byte[] bytes =
                mac.doFinal(("host: " + uri.getHost() + "\n" + "date: " + date + "\n" + "GET " + uri.getPath() + " HTTP/1.1").getBytes(charset));
        String sha = Base64.getEncoder().encodeToString(bytes);

        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                apiKey, "hmac-sha256", "host date request-line", sha);
        log.info(">>>>> AUTHORIZATION: {}", authorization);

        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder().
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(charset))).
                addQueryParameter("date", date).
                addQueryParameter("host", uri.getHost()).
                build();
        return httpUrl.toString();
    }

    static class IflyWebsocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            //连接成功，开始发送数据
            Thread thread = Thread.currentThread();
            log.info(">>>>> CURRENT_THREAD: {}", thread);
            startSendDataAThread(webSocket);

        }

        private void startSendDataAThread(WebSocket webSocket) {
            new Thread(() -> {
                //每一帧音频的大小
                int frameSize = 1280;
                // 音频的状态
                int status = 0;
                try (InputStream inputStream = new FileInputStream(new File(filePath))) {
                    byte[] buffer = new byte[frameSize];
                    // 发送音频
                    end:
                    while (true) {
                        int len = inputStream.read(buffer);
                        if (len == -1) {
                            //文件读完，改变 status 为 2
                            status = lastFrameStatus;
                        }
                        switch (status) {
                            // 第一帧音频 status = 0
                            case firstFrameStatus:
                                //第一帧必须发送
                                JsonObject common = new JsonObject();
                                common.addProperty("app_id", appid);

                                //第一帧必须发送
                                JsonObject business = new JsonObject();
                                business.addProperty("language", "zh_cn");
                                business.addProperty("domain", "iat");
                                //中文方言请在控制台添加试用，添加后即展示相应参数值
                                business.addProperty("accent", "mandarin");
                                //business.addProperty("nunum", 0);
                                //标点符号
                                //business.addProperty("ptt", 0);
                                // zh-cn :简体中文（默认值）zh-hk :繁体香港(若未授权不生效，在控制台可免费开通)
                                //business.addProperty("rlang", "zh-hk");
                                //business.addProperty("vinfo", 1);
                                //动态修正(若未授权不生效，在控制台可免费开通)
                                business.addProperty("dwa", "wpgs");
                                // 句子多候选(若未授权不生效，在控制台可免费开通)
                                //business.addProperty("nbest", 5);
                                // 词级多候选(若未授权不生效，在控制台可免费开通)
                                //business.addProperty("wbest", 3);

                                //每一帧都要发送
                                JsonObject data = new JsonObject();
                                data.addProperty("status", firstFrameStatus);
                                data.addProperty("format", "audio/L16;rate=16000");
                                data.addProperty("encoding", "raw");
                                // data.addProperty("audio", Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                                data.addProperty("audio", "");

                                JsonObject frame = new JsonObject();
                                frame.add("common", common);
                                frame.add("business", business);
                                frame.add("data", data);
                                webSocket.send(frame.toString());

                                // 发送完第一帧改变 status 为 1
                                status = continueFrameStatus;
                                break;

                            //中间帧 status = 1
                            case continueFrameStatus:
                                JsonObject continueFrame = new JsonObject();
                                JsonObject continueData = new JsonObject();
                                continueData.addProperty("status", continueFrameStatus);
                                continueData.addProperty("format", "audio/L16;rate=16000");
                                continueData.addProperty("encoding", "raw");
                                continueData.addProperty("audio", Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                                continueFrame.add("data", continueData);
                                webSocket.send(continueFrame.toString());
                                break;

                            // 最后一帧音频status = 2 ，标志音频发送结束
                            case lastFrameStatus:
                                JsonObject lastData = new JsonObject();
                                lastData.addProperty("status", lastFrameStatus);
                                lastData.addProperty("audio", "");
                                lastData.addProperty("format", "audio/L16;rate=16000");
                                lastData.addProperty("encoding", "raw");
                                JsonObject lastFrame = new JsonObject();
                                lastFrame.add("data", lastData);

                                webSocket.send(lastFrame.toString());
                                break end;
                        }

                        //模拟音频采样延时
                        // Thread.sleep(interval);
                    }
                    log.info(">>>>> ALL DATA SENDED");
                } catch (Exception e) {
                    ErrorPrintUtil.printErrorMsg(log, e);
                }
            }).start();
        }


        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            log.info(">>>>> ON_MESSAGE: {}", text);
            Thread thread = Thread.currentThread();
            log.info(">>>>> CURRENT_THREAD: {}", thread);
            ResponseData response = json.fromJson(text, ResponseData.class);
            if (response != null) {
                if (response.getCode() != 0) {
                    log.info(">>>>> 错误码查询链接：https://www.xfyun.cn/document/error-code");
                    return;
                }
                if (response.getData() != null) {
                    if (response.getData().getResult() != null) {
                        Text result = response.getData().getResult().getText();
                        try {
                            decoder.decode(result);
                            log.info(">>>>> RESULT: {}", decoder.toString());
                        } catch (Exception e) {
                            ErrorPrintUtil.printErrorMsg(log, e);
                        }
                    }
                    if (response.getData().getStatus() == 2) {
                        log.info(">>>>> COST: [{}] MS", System.currentTimeMillis() - begin);
                        log.info(">>>>> FINAL_RESULT: {}", decoder.toString());
                        decoder.discard();
                        // webSocket.close(0, "");
                    }
                }
            }
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable throwable, Response response) {
            super.onFailure(webSocket, throwable, response);
            log.info(">>>>> ON_FAILURE");
            // startSendDataAThread(webSocket);
        }

    }

    public static class ResponseData {
        private int code;
        private String message;
        private String sid;
        private Data data;

        int getCode() {
            return code;
        }

        public String getMessage() {
            return this.message;
        }

        public String getSid() {
            return sid;
        }

        public Data getData() {
            return data;
        }
    }

    public static class Data {
        private int status;
        private Result result;

        int getStatus() {
            return status;
        }

        public Result getResult() {
            return result;
        }
    }

    public static class Result {
        int bg;
        int ed;
        String pgs;
        int[] rg;
        int sn;
        Ws[] ws;
        boolean ls;
        JsonObject vad;

        Text getText() {
            Text text = new Text();
            StringBuilder sb = new StringBuilder();
            for (Ws ws : this.ws) {
                sb.append(ws.cw[0].w);
            }
            text.sn = this.sn;
            text.text = sb.toString();
            text.rg = this.rg;
            text.pgs = this.pgs;
            text.bg = this.bg;
            text.ed = this.ed;
            text.ls = this.ls;
            text.vad = this.vad;
            return text;
        }
    }

    public static class Ws {
        Cw[] cw;
        int bg;
        int ed;
    }

    public static class Cw {
        int sc;
        String w;
    }

    public static class Text {
        int sn;
        int bg;
        int ed;
        String text;
        String pgs;
        int[] rg;
        boolean deleted;
        boolean ls;
        JsonObject vad;

        @Override
        public String toString() {
            return "Text{" +
                    "bg=" + bg +
                    ", ed=" + ed +
                    ", ls=" + ls +
                    ", sn=" + sn +
                    ", text='" + text + '\'' +
                    ", pgs=" + pgs +
                    ", rg=" + Arrays.toString(rg) +
                    ", deleted=" + deleted +
                    ", vad=" + (vad == null ? "null" : vad.getAsJsonArray("ws").toString()) +
                    '}';
        }
    }

    //解析返回数据，仅供参考
    public static class Decoder {
        private Text[] texts;
        private int defc = 10;

        Decoder() {
            this.texts = new Text[this.defc];
        }

        synchronized void decode(Text text) {
            if (text.sn >= this.defc) {
                this.resize();
            }
            if ("rpl".equals(text.pgs)) {
                for (int i = text.rg[0]; i <= text.rg[1]; i++) {
                    this.texts[i].deleted = true;
                }
            }
            this.texts[text.sn] = text;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Text t : this.texts) {
                if (t != null && !t.deleted) {
                    sb.append(t.text);
                }
            }
            return sb.toString();
        }

        void resize() {
            int oc = this.defc;
            this.defc <<= 1;
            Text[] old = this.texts;
            this.texts = new Text[this.defc];
            if (oc >= 0) System.arraycopy(old, 0, this.texts, 0, oc);
        }

        void discard() {
            for (int i = 0; i < this.texts.length; i++) {
                this.texts[i] = null;
            }
        }
    }

}

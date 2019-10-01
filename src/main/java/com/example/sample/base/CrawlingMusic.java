package com.example.sample.base;

import com.example.sample.util.HttpURLConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CrawlingMusic {

    // private static Logger logger = LoggerFactory.getLogger(CrawlingMusic.class);

    private String search(String songName) throws Exception {
        String url = "https://music.sonimei.cn";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("accept", "application/json,text/javascript,*/*; q=0.01");
        headerMap.put("accept-encoding", "gzip, deflate, br");
        headerMap.put("accept-language", "zh-CN,zh;q=0.9");
        headerMap.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerMap.put("origin", "https://music.sonimei.cn");
        headerMap.put("referer", "https://music.sonimei.cn");
        headerMap.put("sec-fetch-mode", "cors");
        headerMap.put("sec-fetch-site", "same-origin");
        headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.87 Safari/537.36");
        headerMap.put("x-requested-with", "XMLHttpRequest");

        String type = "netease";
        // String type = "qq";
        // String type = "kugou";
        // String type = "kuwo";
        // String type = "xiami";
        // String type = "baidu";
        // String type = "1ting";
        // String type = "migu";
        String param = "input=" + songName + "&filter=name&type=" + type + "&page=1";
        return HttpURLConnectionUtil.httpPost(url, headerMap, param);
    }

    @Test
    public void searchMusic() throws Exception {
        String songName = "记事本";
        String result = search(songName);
        log.info(">>>>> RESULT: {}", result);
        // {"data":[{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/3177952","songid":"3177952","title":"\u5bfb\u6c34\u7684\u9c7c","author":"\u6768\u5c18","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_3177952&response=res&type=convert_url&","pic":"http:\/\/img3.kuwo.cn\/star\/starheads\/120\/8\/abff114c20a001effc2c485c98f8263_0.jpg"},{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/70055040","songid":"70055040","title":"\u5bfb\u6c34\u7684\u9c7c(live)","author":"\u9708\u4e39","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_70055040&response=res&type=convert_url&","pic":"http:\/\/img4.kuwo.cn\/star\/starheads\/120\/1\/18b979ff87ee6e77c0748cbdf3d319d_0.jpg"},{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/23374157","songid":"23374157","title":"\u5bfb\u6c34\u7684\u9c7c","author":"\u5c0f\u6d32","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_23374157&response=res&type=convert_url&","pic":"http:\/\/img2.kuwo.cn\/star\/starheads\/120\/2\/69845b7acd4880b617ddd5e3d04f72d_0.jpg"},{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/23904820","songid":"23904820","title":"\u5bfb\u6c34\u7684\u9c7c","author":"\u83f2\u513f","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_23904820&response=res&type=convert_url&","pic":"http:\/\/img4.kuwo.cn\/star\/starheads\/120\/0\/383d061a00fc69943f4748cfc41d30c_0.jpg"},{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/67962963","songid":"67962963","title":"\u5bfb\u6c34\u7684\u9c7c (Live)","author":"\u9708\u4e39","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_67962963&response=res&type=convert_url&","pic":"http:\/\/img2.kuwo.cn\/star\/starheads\/120\/1\/18b979ff87ee6e77c0748cbdf3d319d_0.jpg"},{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/19087239","songid":"19087239","title":"\u5bfb\u6c34\u7684\u9c7c(Live\u7248)","author":"\u83f2\u513f","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_19087239&response=res&type=convert_url&","pic":"http:\/\/img4.kuwo.cn\/star\/starheads\/120\/0\/383d061a00fc69943f4748cfc41d30c_0.jpg"},{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/51609798","songid":"51609798","title":"\u5bfb\u6c34\u7684\u9c7c (Live)","author":"\u5c0f\u6d32","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_51609798&response=res&type=convert_url&","pic":"http:\/\/img2.kuwo.cn\/star\/starheads\/120\/2\/69845b7acd4880b617ddd5e3d04f72d_0.jpg"},{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/71604594","songid":"71604594","title":"\u5bfb\u6c34\u7684\u9c7c (Live)","author":"\u5ba3\u5ba3","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_71604594&response=res&type=convert_url&","pic":"http:\/\/img2.kuwo.cn\/star\/starheads\/120\/1\/5dd5d5756eb99df4eef1ee52805b428_0.jpg"},{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/57590632","songid":"57590632","title":"\u5bfb\u6c34\u7684\u9c7c","author":"\u5e84\u68a6\u8776","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_57590632&response=res&type=convert_url&","pic":"http:\/\/img4.kuwo.cn\/star\/starheads\/120\/4\/34f026f2b85b104742bbdb72ca61773_0.jpg"},{"type":"kuwo","link":"http:\/\/www.kuwo.cn\/yinyue\/22849406","songid":"22849406","title":"\u5bfb\u6c34\u7684\u9c7c","author":"\u521d\u604bCyan","lrc":null,"url":"http:\/\/antiserver.kuwo.cn\/anti.s?useless=\/resource\/&format=mp3&rid=MUSIC_22849406&response=res&type=convert_url&","pic":"http:\/\/img2.kuwo.cn\/star\/starheads\/120\/4\/2266a552e45ea728c08345e0c368868_0.jpg"}],"code":200,"error":""}
    }

    @Test
    public void downloadMusic() throws Exception {
        // // String url = "http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_3177952&response=res&type=convert_url&";
        // String url = "http://music.163.com/song/media/outer/url?id=189986.mp3";
        // String downloadDir = "d:/download/";
        // HttpURLConnectionUtil.download(url, downloadDir);
        // logger.info(">>>>> DOWNLOAD DONE");
    }

}

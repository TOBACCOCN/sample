package com.example.sample.base;

import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.HttpURLConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CrawlingCommentsAboutDivorceOfMayiliAndWenzhang {

    // private static Logger logger = LoggerFactory.getLogger(CrawlingCommentsAboutDivorceOfMayiliAndWenzhang.class);

    public static void main(String[] args) throws Exception {
        // 根据一次请求数据，总页数 2600 多一点，后续应该会不断增加
        int pages = 2600;

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie",
                "SUB=_2AkMqYlDef8NxqwJRmP8cymrnbYl1zwHEieKcPqEFJRMxHRl-yT83qhMrtRB6AeJ-MASXRWde_foloxGBb5CNiT07KF-D; " +
                        "SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9Wh.4Or5gWHEJFp3EXAY66J.; " +
                        "YF-V5-G0=7e17aef9f70cd5c32099644f32a261c4");
        String filePath = "D:/mayili.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (int i = 1; i <= pages; i++) {
            log.info(">>>>> PAGE: [{}]", i);
            String url = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=4399042567665659&page=" + i;
            String result = HttpURLConnectionUtil.httpGet(url, headerMap);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String html = jsonObject.getJSONObject("data").getString("html");
            log.info(">>>>> HTML: [{}]", html);
            Document document = Jsoup.parse(html);
            Elements elements = document.select("div.WB_text");
            for (Element element : elements) {
                String text = element.text();
                String[] array = text.split("：");
                if (array.length > 1) {
                    String comment = array[1];
                    log.info(">>>>> COMMENT: [{}]", comment);
                    writer.write(comment);
                    writer.newLine();
                    writer.flush();
                }
            }
            Thread.sleep(10);
        }
        writer.close();
    }

}

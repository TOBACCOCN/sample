package com.example.sample.trans.microsoft;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MicrosoftTranslation {

    // private static Logger logger = LoggerFactory.getLogger(MicrosoftTranslation.class);

    public static void main(String[] args) throws IOException {
        // 全局
        // String endpoint = "https://api.cognitive.microsofttranslator.com";
        // 美国
        // String endpoint = "https://api-nam.cognitive.microsofttranslator.com";
        // 欧洲
        // String endpoint = "https://api-eur.cognitive.microsofttranslator.com";
        // 亚太区
        String endpoint = "https://api-apc.cognitive.microsofttranslator.com";
        String url = endpoint + "/translate?api-version=3.0";
        String subscriptionKey = "";
        String from = "en";
        String to = "zh-Hans";
        String text = "Welcome to Microsoft Translator. Guess how many languages I speak!";
        ResponseBody response = post(url, subscriptionKey, from, to, text);
        log.info(">>>> RESULT: {}", response == null ? null : response.string());
    }

    private static ResponseBody post(String url, String subscriptionKey, String from, String to, String text) throws IOException {
        // RequestBody body = RequestBody.create(mediaType,
        //         "[{\"Text\": \"Welcome to Microsoft Translator. Guess how many languages I speak!\"}]");
        List<Map<String, String>> list = new ArrayList<>();
        url = url + "&from=" + from + "&to=" + to;
        Map<String, String> map = new HashMap<>();
        map.put("Text", text);
        list.add(map);
        RequestBody body =
                RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(list));
        // RequestBody body =
        //         RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(map));

        // australiaeast、brazilsouth、canadacentral、centralindia、eastasia、eastus、
        // japaneast、northeurope、southcentralus、southeastasia、uksouth、westcentralus、
        // westeurope、westus、westus2
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Ocp-Apim-Subscription-Region", "westcentralus")
                .addHeader("Content-type", "application/json").build();
        Response response = new OkHttpClient().newCall(request).execute();
        return response.body();
    }

}

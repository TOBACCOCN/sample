package com.example.sample.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.HttpURLConnectionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryWeather {

    // private static Logger logger = LoggerFactory.getLogger(QueryWeather.class);

    public static void main(String[] args) throws Exception {
        String city = "beijing";
        // String city = "北京";
        // String city = "101010100";      // cityid
        String url = "https://www.tianqiapi.com/api/?version=v1&city=" + city + "&appid=1001&appsecret=5566";
        String result = HttpURLConnectionUtil.httpGet(url, null);
        JSONObject todayWeatherJson = (JSONObject) JSON.parseObject(result).getJSONArray("data").get(0);
        log.info(">>>>> DATE: {}", todayWeatherJson.getString("date"));
        log.info(">>>>> WEATHER: {}", todayWeatherJson.getString("wea"));
        log.info(">>>>> AIR_LEVEL: {}", todayWeatherJson.getString("air_level"));
        log.info(">>>>> HIGH_TEMP: {}", todayWeatherJson.getString("tem1"));
        log.info(">>>>> LOW_TEMP: {}", todayWeatherJson.getString("tem2"));
        log.info(">>>>> AVERAGE_TEMP: {}", todayWeatherJson.getString("tem"));
    }

}

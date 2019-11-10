package com.example.sample.base;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class CalculatorDistance {

    // private static Logger logger = LoggerFactory.getLogger(CalculatorDistance.class);

    public static void main(String[] args) {
        // String start = "浙江省杭州市西湖区";
        // String end = "郑州市金水区";
        // String startLngLat = getLngLat(start);
        // String endLngLat = getLngLat(end);
        // log.info(">>>>> START_LNGLAT: [{}]", startLngLat);
        // log.info(">>>>> END_LNGLAT: [{}]", endLngLat);

        String startLngLat = "113.924624,22.504315";
        String endLngLat = "113.924002,22.502828";

        Long distance = getDistance(startLngLat, endLngLat);
        log.info(">>>>> DISTANCE: [{}]", distance);
    }

    private static String getLngLat(String address) {
        // 返回输入地址 address 的经纬度信息, 格式是 经度,纬度
        String url = "http://restapi.amap.com/v3/geocode/geo?key=508eb8fd7521df6de675d0b09e6179b0&address="
                + address;
        String result = sendHttpRequest(url); // 高德接口返回的是 JSON 格式的字符串

        JSONArray ja = JSONObject.fromObject(result).getJSONArray("geocodes");
        return JSONObject.fromObject(ja.getString(0)).get("location").toString();
    }

    private static Long getDistance(String startLngLat, String endLngLat) {
        // 返回起始地 startLonLat 与目的地 endAddr 之间的距离，单位：米
        String url = "http://restapi.amap.com/v3/distance?key=508eb8fd7521df6de675d0b09e6179b0&origins="
                + startLngLat + "&destination=" + endLngLat;
        String result = sendHttpRequest(url);
        JSONArray jsonArray = JSONObject.fromObject(result).getJSONArray("results");
        return Long.parseLong(JSONObject.fromObject(jsonArray.getString(0)).get("distance").toString());
    }

    private static String sendHttpRequest(String requestUrl) {
        // 用 JAVA 发起 http 请求，并返回 json 格式的结果
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
        return result.toString();
    }

}
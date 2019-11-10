package com.example.sample.trans.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alimt.model.v20181012.TranslateECommerceRequest;
import com.aliyuncs.alimt.model.v20181012.TranslateECommerceResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;

@Slf4j
public class AliyunTranslation {

    // private static Logger logger = LoggerFactory.getLogger(AliyunTranslation.class);

    public static void main(String[] args) {
        // 使用您的阿里云访问密钥 AccessKeyId
        String accessKeyId = "";
        // 使用您的阿里云访问密钥
        String accessKeySecret = "";
        // 创建DefaultAcsClient实例并初始化
        try {
            DefaultProfile profile = DefaultProfile.getProfile(
                    // 地域 ID
                    "cn-hangzhou",
                    // 阿里云账号的 AccessKey ID
                    accessKeyId,
                    // 阿里云账号 Access Key Secret
                    accessKeySecret);

            IAcsClient client = new DefaultAcsClient(profile);
            // 创建 API 请求并设置参数
            TranslateECommerceRequest eCommerceRequest = new TranslateECommerceRequest();
            eCommerceRequest.setScene("title");
            // 设置请求方式，POST
            eCommerceRequest.setMethod(MethodType.POST);
            //翻译文本的格式
            eCommerceRequest.setFormatType("text");
            //源语言
            eCommerceRequest.setSourceLanguage("zh");
            //原文
            eCommerceRequest.setSourceText(URLEncoder.encode("欢迎使用阿里云智能语音合成服务，您可以说北京明天天气怎么样啊", "UTF-8"));
            //目标语言
            eCommerceRequest.setTargetLanguage("en");
            TranslateECommerceResponse eCommerceResponse = client.getAcsResponse(eCommerceRequest);
            log.info(">>>>> TRANSLATION_RESULT: [{}]", JSONObject.toJSON(eCommerceResponse));
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
    }
}

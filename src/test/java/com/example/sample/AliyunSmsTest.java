package com.example.sample;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class AliyunSmsTest {

    @Test
    public void sendSms() throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "", "");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", "");
        request.putQueryParameter("SignName", "");
        request.putQueryParameter("TemplateCode", "");
        // TemplateParam 对应的值是一个 JSON 串，JSON 串中的 key 是模板中的变量名
        request.putQueryParameter("TemplateParam", "{\"code\":\"1111\"}");
        CommonResponse response = client.getCommonResponse(request);
        log.debug(">>>>> RESULT: [{}]", response.getData());
        // {"Message":"OK","RequestId":"EDAC1CBF-A625-4F61-A8E9-C2935B81BAC3","BizId":"782619176396333347^0","Code":"OK"}
    }

}

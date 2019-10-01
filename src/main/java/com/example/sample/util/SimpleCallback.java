package com.example.sample.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class SimpleCallback implements Callback {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    // private static Logger logger = LoggerFactory.getLogger(SimpleCallback.class);

    @Override
    public void onFailure(Call call, IOException e) {
        ErrorPrintUtil.printErrorMsg(log, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        this.response = response;
    }
}

package com.example.sample.netty.http;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.ObjectUtil;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UrlencodedStringEncoder {
	
    private static final Pattern PATTERN = Pattern.compile("+", Pattern.LITERAL);
    private Charset charset;
    private final String uri;
    private List<Param> params = new ArrayList<>();
    
    public UrlencodedStringEncoder(String uri) {
    	this.uri = uri;
    	this.charset = HttpConstants.DEFAULT_CHARSET;
    }
    
    /**
     * Adds a parameter with the specified name and value to this encoder.
     */
    public void addParam(String name, String value) {
        ObjectUtil.checkNotNull(name, "name");
        params.add(new Param(name, value));
    }
    
    public URI toUri() throws URISyntaxException {
    	return new URI(uri);
    }
    
	public String toString() {
        if (params.isEmpty()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < params.size(); i++) {
                Param param = params.get(i);
                sb.append(encodeComponent(param.name, charset));
                if (param.value != null) {
                    sb.append('=');
                    sb.append(encodeComponent(param.value, charset));
                }
                if (i != params.size() - 1) {
                    sb.append('&');
                }
            }
            return sb.toString();
        }
	}
	
    private static String encodeComponent(String s, Charset charset) {
        try {
            return URLEncoder.encode(s, PATTERN.matcher(charset.name()).replaceAll("%20"));
        } catch (UnsupportedEncodingException ignored) {
            throw new UnsupportedCharsetException(charset.name());
        }
    }
	
    private static final class Param {

        final String name;
        final String value;

		Param(String name, String value) {
            this.value = value;
            this.name = name;
        }
    }
    
    public static void main(String[] args) {
    	UrlencodedStringEncoder encoder = new UrlencodedStringEncoder("/helloworld");
    	encoder.addParam("name", "zyh");
    	System.out.println(encoder.toString());
    }

}

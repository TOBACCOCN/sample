package com.example.demo.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;  
  
/**
 * 编解码工厂
 * @author zhangyonghong
 * @date 2018.3.17
 *
 */
public class HCodecFactory implements ProtocolCodecFactory {
	
    private final HEncoder encoder;
    
    private final HDecoder decoder;  
  
    public HCodecFactory() {  
        //this(Charset.defaultCharset());  
        this(Charset.forName("UTF-8"));  
  
    }  
  
    public HCodecFactory(Charset charSet) {  
        this.encoder = new HEncoder(charSet);  
        this.decoder = new HDecoder(charSet);  
    }  
  
    @Override  
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {  
        return decoder;  
    }  
  
    @Override  
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {  
        return encoder;  
    }  
}
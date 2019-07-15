package com.example.sample.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 编解码工厂
 *
 * @author zhangyonghong
 * @date 2018.3.17
 */
public class SimpleCodecFactory implements ProtocolCodecFactory {

    private final SimpleProtocolEncoder encoder;
    private final SimpleProtocolDecoder decoder;

    public SimpleCodecFactory() {
        this(StandardCharsets.UTF_8);
    }

    public SimpleCodecFactory(Charset charSet) {
        this.encoder = new SimpleProtocolEncoder(charSet);
        this.decoder = new SimpleProtocolDecoder(charSet);
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) {
        return decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) {
        return encoder;
    }
}
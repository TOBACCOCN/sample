package com.example.sample.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * 解码工具类
 *
 * @author zhangyonghong
 * @date 2018.3.17
 */
public class SimpleProtocolDecoder extends ProtocolDecoderAdapter {

    private final Charset charset;

    public SimpleProtocolDecoder(Charset charset) {
        this.charset = charset;
    }

    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        CharsetDecoder cd = charset.newDecoder();
        String mes = in.getString(cd);
        out.write(mes);
    }

} 
package com.example.sample.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;


/**
 * 编码工具类
 *
 * @author zhangyonghong
 * @date 2018.3.17
 */
public class SimpleProtocolEncoder extends ProtocolEncoderAdapter {

    private Charset charset;

    public SimpleProtocolEncoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        CharsetEncoder ce = charset.newEncoder();
        String mes = (String) message;
        IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
        buffer.putString(mes, ce);
        buffer.flip();
        out.write(buffer);
    }

}
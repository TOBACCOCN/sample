package com.example.sample.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 音频文件工具类
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
public class AudioUtil {

    /**
     * wav 音频文件转 pcm 音频文件
     *
     * @param wavPath wav 音频文件路径
     * @param pcmPath pcm 音频文件路径
     */
    public static void wav2Pcm(String wavPath, String pcmPath) throws Exception {
        FileInputStream fis = new FileInputStream(wavPath);
        FileOutputStream fos = new FileOutputStream(pcmPath);
        byte[] wavBytes = inputStreamToBytes(fis);
        byte[] pcmBytes = Arrays.copyOfRange(wavBytes, 44, wavBytes.length);
        fos.write(pcmBytes);
        fis.close();
        fos.close();
    }

    /**
     * 输入流转字节数组
     *
     * @param fis 输入流
     * @return 字节数组
     */
    private static byte[] inputStreamToBytes(FileInputStream fis) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        byte[] data = baos.toByteArray();
        baos.close();
        return data;
    }

    /**
     * pcm 音频文件转 wav 音频文件
     *
     * @param pcmPath pcm 音频文件路径
     * @param wavPath wav 音频文件路径
     */
    public static void pcm2Wav(String pcmPath, String wavPath) throws Exception {
        FileInputStream fis = new FileInputStream(pcmPath);
        FileOutputStream fos = new FileOutputStream(wavPath);

        byte[] buf = new byte[1024 * 8];
        int length = fis.read(buf);
        int pcmLength = 0;
        while (length != -1) {
            pcmLength += length;
            length = fis.read(buf);
        }
        fis.close();

        int fileLength = pcmLength + (44 - 8);
        short channels = 1;
        // short channels = 2;
        short bitsPerSample = 16;
        short blockAlign = (short) (channels * bitsPerSample);
        // int sampleRate = 8000;
        int sampleRate = 16000;
        // int sampleRate = 24000;
        // int sampleRate = 44100;
        int avgBytesPerSec = blockAlign * sampleRate;
        int dataHdrLength = pcmLength;
        WavHeader header = new WavHeader(fileLength, channels, sampleRate, avgBytesPerSec, blockAlign, bitsPerSample, dataHdrLength);

        byte[] headerBytes = header.getHeader();
        assert headerBytes.length == 44;
        fos.write(headerBytes, 0, headerBytes.length);
        fis = new FileInputStream(pcmPath);
        length = fis.read(buf);
        while (length != -1) {
            fos.write(buf, 0, length);
            length = fis.read(buf);
        }
        fis.close();
        fos.close();
    }

}

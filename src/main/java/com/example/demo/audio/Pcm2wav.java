package com.example.demo.audio;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Pcm2wav {

	public static void main(String[] args) throws Exception {
		pcm2wav("C:\\Users\\Administrator\\Desktop\\pcm.pcm", "C:\\Users\\Administrator\\Desktop\\1.wav");
	}

	private static void pcm2wav(String pcmPath, String wavPath) throws Exception {
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
//		short channels = 2;
		short bitsPerSample = 16;
		short blockAlign = (short) (channels * bitsPerSample);
//		int sampleRate = 8000;
		int sampleRate = 16000;
//		int sampleRate = 44100;
		int avgBytesPerSec = blockAlign * sampleRate;
		int dataHdrLength = pcmLength;
		WaveHeader header = new WaveHeader(fileLength, channels, sampleRate, avgBytesPerSec, blockAlign, bitsPerSample, dataHdrLength);

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

class WaveHeader {
	private char fileID[] = { 'R', 'I', 'F', 'F' }; 		// 4byte, 资源交换文件标识
	private int fileLength; 										// 4byte, 从下个地址开始到文件尾的总字节数
	private char wavTag[] = { 'W', 'A', 'V', 'E' }; 	// 4byte, WAV文件标识
	private char fmtHdrID[] = { 'f', 'm', 't', ' ' }; 	// 4byte, 波形格式标识(fmt), 最后一位空格
	private int fmtHdrLength = 16; 							// 4byte, PCMWAVEFORMAT的长度
	private short formatTag = 1; 								// 2byte, 格式种类(值为1时, 表示数据为线性PCM编码)
	private short channels; 										// 2byte, 通道数, 单声道为1, 双声道为2
	private int sampleRate; 										// 4byte, 采样率, 比如44100
	private int avgBytesPerSec; 								// 4byte, 波形数据传输速率(每秒平均字节数), 大小为 通道数 * 采样位数
	private short blockAlign; 									// 2byte, DATA数据块长度, 大小为 通道数 * 采样位数
	private short bitsPerSample; 							// 2byte, 采样位数, 即PCM位宽, 通常为8位或16位
	private char dataHdrID[] = { 'd', 'a', 't', 'a' }; // 4byte, 数据标记符
	private int dataHdrLength; 								// 4byte, 接下来声音数据的总大小
	
	public WaveHeader(int fileLength, short channels, int sampleRate, int avgBytesPerSec, short blockAlign, short bitsPerSample,
			int dataHdrLength) {
		super();
		this.fileLength = fileLength;
		this.channels = channels;
		this.sampleRate = sampleRate;
		this.avgBytesPerSec = avgBytesPerSec;
		this.blockAlign = blockAlign;
		this.bitsPerSample = bitsPerSample;
		this.dataHdrLength = dataHdrLength;
	}

	public byte[] getHeader() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		WriteChar(baos, fileID);
		WriteInt(baos, fileLength);
		WriteChar(baos, wavTag);
		WriteChar(baos, fmtHdrID);
		WriteInt(baos, fmtHdrLength);
		WriteShort(baos, formatTag);
		WriteShort(baos, channels);
		WriteInt(baos, sampleRate);
		WriteInt(baos, avgBytesPerSec);
		WriteShort(baos, blockAlign);
		WriteShort(baos, bitsPerSample);
		WriteChar(baos, dataHdrID);
		WriteInt(baos, dataHdrLength);
		baos.flush();
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;
	}

	private void WriteShort(ByteArrayOutputStream baos, int s) throws IOException {
		byte[] mybyte = new byte[2];
		mybyte[1] = (byte) ((s << 16) >> 24);
		mybyte[0] = (byte) ((s << 24) >> 24);
		baos.write(mybyte);
	}

	private void WriteInt(ByteArrayOutputStream baos, int i) throws IOException {
		byte[] buf = new byte[4];
		buf[3] = (byte) (i >> 24);
		buf[2] = (byte) ((i << 8) >> 24);
		buf[1] = (byte) ((i << 16) >> 24);
		buf[0] = (byte) ((i << 24) >> 24);
		baos.write(buf);
	}

	private void WriteChar(ByteArrayOutputStream baos, char[] cArray) {
		for (int i = 0; i < cArray.length; i++) {
			char c = cArray[i];
			baos.write(c);
		}
	}
}

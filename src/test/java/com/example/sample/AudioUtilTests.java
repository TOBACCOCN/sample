package com.example.sample;

import com.example.sample.util.AudioUtil;
import org.junit.Test;

public class AudioUtilTests {

    @Test
    public void pcm2Wav() throws Exception {
        AudioUtil.pcm2Wav("D:\\git\\github\\sample\\output.pcm", "D:\\git\\github\\sample\\output.wav");
    }

}

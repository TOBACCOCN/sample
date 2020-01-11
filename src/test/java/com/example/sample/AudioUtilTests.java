package com.example.sample;

import com.example.sample.util.AudioUtil;
import org.junit.Test;

public class AudioUtilTests {

    @Test
    public void pcm2Wav() throws Exception {
        AudioUtil.pcm2Wav("C:\\Users\\Administrator\\Desktop\\1.pcm",
                "C:\\Users\\Administrator\\Desktop\\1.wav");
    }

}

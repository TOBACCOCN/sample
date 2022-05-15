package com.example.sample;

import com.carrotsearch.sizeof.RamUsageEstimator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SizeofTest {

    @Test
    public void sizeof() {
        List<Device> list = new ArrayList<>();
        list.add(new Device("1234567890123456789012345", "abcde", "fghij"));    // 312
        list.add(new Device("1234567890123456789012346", "abcde", "fghij"));    // 432
        list.add(new Device("1234567890123456789012347", "abcde", "fghij"));    // 552
        list.add(new Device("1234567890123456789012348", "abcde", "fghij"));    // 672
        long l = RamUsageEstimator.sizeOf(list);
        log.info(">>>>> size: [{}]", l);
    }

}

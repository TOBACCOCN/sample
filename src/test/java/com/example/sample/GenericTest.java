package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GenericTest {

    @Test
    public void superClassOrInterfaces() {
        List<String> list = new ArrayList<>();
        Class<?> superclass = list.getClass().getSuperclass();
        log.info("[{}]", superclass);
        Type type = list.getClass().getGenericSuperclass();
        log.info("[{}]", type);
        Class<?>[] interfaces = list.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            log.info("[{}]", clazz);
        }
    }

}

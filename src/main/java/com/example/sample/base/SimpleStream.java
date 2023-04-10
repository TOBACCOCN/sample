package com.example.sample.base;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public class SimpleStream {

    public static void forEach(
            Map<String, ? extends Object> map, BiConsumer<Integer, Map.Entry> action) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(action);

        int index = 1;
        for (Map.Entry entry : map.entrySet()) {
            action.accept(index++, entry);
        }
    }

}

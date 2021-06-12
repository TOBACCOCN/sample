package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.Clock;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Java8Test {

    // private static Logger logger = LoggerFactory.getLogger(Java8Tests.class);

    @Test
    public void test() throws ScriptException {
        List<Integer> list = Arrays.asList(8, 9, 8, 1, 1, 1, 4);
        Map<Integer, Integer> map = new HashMap<>();
        // 串行流,顺序固定
        list.forEach(e -> log.info(">>>>> {}", e));
        list.forEach(e -> map.put(e, e));
        // 并行流, 顺序不固定
        list.parallelStream().forEach(e -> log.info(">>>>> {}", e));
        // 过滤
        list.stream().filter(n -> n > 6)/* .collect(Collectors.toList()) */.forEach(e -> log.info(">>>>> {}", e));
        Map<Integer, Integer> filterMap = map.entrySet().stream().filter(entry -> entry.getKey() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        filterMap.forEach((key, value) -> log.info(">>>>> {} = {}", key, value));
        // 排序
        list.stream().sorted().forEach(e -> log.info(">>>>> {}", e));
        // Collections.sort(list, (e1, e2) ->Integer.compare(e1, e2));
        // 最大值
        Optional<Integer> max = list.stream().max(Comparator.comparingInt(n -> n));
        Optional<Integer> max2 = list.stream().max((n1, n2) -> (n2 - n1));
        log.info(">>>>> max: [{}]", max.get());
        log.info(">>>>> max2: [{}]", max2.get());
        // 最小值
        Optional<Integer> min = list.stream().min(Comparator.comparingInt(n -> n));
        Optional<Integer> min2 = list.stream().min((n1, n2) -> (n2 - n1));
        log.info(">>>>> min: [{}]", min.get());
        log.info(">>>>> min2: [{}]", min2.get());
        // 映射-规纳
        Optional<Integer> reduce = list.stream().map(n -> n * n).reduce(Integer::sum);
        log.info(">>>>> REDUCE: [{}]", reduce.get());
        Map<Integer, Long> collect = list.stream()
                .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
        // 分组
        // MapUtils.verbosePrint(System.out, null, collect);
        collect.forEach((key, value) -> log.info(">>>>> value: [{}], counts: [{}]", key, value));
        // 去重－统计
        log.info(">>>>> COUNT: [{}]", list.stream().distinct().count());
        new Thread(() -> log.info(">>>>> THREAD STARTING")).start();
        // 可分割迭代器
        Spliterator<Integer> spliterator = list.stream().spliterator();
        while (spliterator.tryAdvance(e -> log.info(">>>>> {}", e))) ;
        list.stream().spliterator().forEachRemaining(e -> log.info(">>>>> {}", e));
        // Java8 Date/Time
        log.info(">>>>> UTC: [{}]", Clock.systemUTC().instant());
        log.info(">>>>> MINI_SECONDS: [{}]", Clock.systemUTC().millis());
        // Java8 Base64 内置
        log.info(">>>>> BASE64 OF [{}]: [{}]", "zhangyonghong", Base64.getEncoder().encodeToString("zhangyonghong".getBytes()));
        log.info(">>>>> BASE64_DECODER OF [{}]: [{}]", "emhhbmd5b25naG9uZw==", new String(Base64.getDecoder().decode("emhhbmd5b25naG9uZw==")));
        // 可以运行js代码的引擎
        log.info(">>>>> JAVA_SCRIPT: [{}]",
                new ScriptEngineManager().getEngineByName("JavaScript").eval("function f() {return 10;}f()*10;"));
    }

}

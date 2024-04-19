package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Java8Test {

	// private static Logger logger = LoggerFactory.getLogger(Java8Tests.class);

	@Test
	public void stream() {
		List<Integer> list = Arrays.asList(8, 9, 8, 1, 1, 1, 4);
		Map<Integer, Integer> map = new HashMap<>();
		// 串行流,顺序固定
		list.stream().forEach(e -> log.info(">>>>> {}", e));
		list.forEach(e -> map.put(e, e));

		// 并行流, 顺序不固定
		list.parallelStream().forEach(e -> log.info(">>>>> {}", e));

		// peek
		// 与 forEach 的区别是：
		// peek like this: A wakeup->wash->breakfast, B wakeup->wash->breakfast;
		// forEach like this: A wakeup, B wakeup; A wash, B wash; A breakfast, B breakfast;
		list.stream().peek(e->log.info("{}", e)).forEach(e->log.info("{}", e));

		// 过滤
		list.stream().filter(n -> n > 6)/* .collect(Collectors.toList()) */.forEach(e -> log.info(">>>>> {}", e));
		Map<Integer, Integer> filterMap = map.entrySet().stream().filter(entry -> entry.getKey() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		filterMap.forEach((key, value) -> log.info(">>>>> {} = {}", key, value));

		// 排序
		list.stream().sorted().forEach(e -> log.info(">>>>> {}", e));
		// Collections.sort(list, (e1, e2) ->Integer.compare(e1, e2));

		// 最大值
		Optional<Integer> maxOp = list.stream().max(Comparator.comparingInt(n -> n));
		// maxOp = list.stream().sorted((n1, n2) -> n2 - n1).findFirst();
		maxOp.ifPresent(max -> log.info(">>>>> max: [{}]", max));

		// 最小值
		Optional<Integer> minOp = list.stream().min(Comparator.comparingInt(n -> n));
		// minOp = list.stream().sorted((n1, n2) -> n1 - n2).findFirst();
		minOp.ifPresent(min -> log.info(">>>>> min: [{}]", min));

		// 映射-规纳
		Optional<Integer> optional = list.stream().map(n -> n * n).reduce(Integer::sum);
		optional.ifPresent(sum -> log.info(">>>>> REDUCE_RESULT: [{}]", sum));
		Integer initial = 100;
		Integer result = list.stream().map(n -> n * n).reduce(initial, Integer::sum);
		log.info(">>>>> REDUCE_RESULT: [{}]", result);
		Double doubleInitial = 100.0D;
		Double doubleResult = Stream.of("1", "2", "3").reduce(doubleInitial, (d, s) -> Double.parseDouble(s), Double::sum);
		log.info(">>>>> REDUCE_RESULT: [{}]", doubleResult);

		// flatMap
		List<List<String>> l = new ArrayList<>();
		l.add(new ArrayList<String>(){{add("a");add("b");add("c");}});
		l.add(new ArrayList<String>(){{add("A");add("B");add("C");}});
		l.stream().flatMap(Collection::stream).forEach(log::info);

		// 去重－统计
		log.info(">>>>> COUNT: [{}]", list.stream().distinct().count());

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

		// lambda
		new Thread(() -> log.info(">>>>> THREAD STARTING")).start();
	}


	@Test
	public void script() throws IOException, ScriptException, NoSuchMethodException {
		// 运行 js 代码
		log.info(">>>>> JAVA_SCRIPT: [{}]",
				new ScriptEngineManager().getEngineByName("JavaScript").eval("function f() {return 10;}f()*10;"));

		// 运行 js 文件中的代码
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		String path = "C:\\Users\\TOBACCO\\Desktop\\javascript\\hello.js";     // function hello(arg) {return "hello " +arg}
		engine.eval(new FileReader(path));
		Object result = ((Invocable) engine).invokeFunction("hello", new Object[]{"JavaScript"});
		log.info(">>>>> RESULT: [{}]", result);
	}

}

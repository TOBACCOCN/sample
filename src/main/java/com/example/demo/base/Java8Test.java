package com.example.demo.base;

import java.time.Clock;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Collectors;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.collections.MapUtils;

public class Java8Test {

	public static void main(String[] args) throws ScriptException {
		List<Integer> list = Arrays.asList(8, 9, 8, 1, 1, 1, 4);
		// 串行流,顺序固定
		list.stream().forEach(e -> System.out.println(e));
		// 并行流, 顺序不固定
		list.parallelStream().forEach(System.out::println);
		// 过滤
		list.stream().filter(n -> n > 6)/* .collect(Collectors.toList()) */.forEach(System.out::println);
		// 排序
		list.stream().sorted().forEach(System.out::println);
		// Collections.sort(list, (e1, e2) ->Integer.compare(e1, e2));
		// 最大值
		Optional<Integer> max = list.stream().max((n1, n2) -> (n1 - n2));
		Optional<Integer> max2 = list.stream().max((n1, n2) -> (n2 - n1));
		System.out.println("max:" + max.get());
		System.out.println("max2:" + max2.get());
		// 最小值
		Optional<Integer> min = list.stream().min((n1, n2) -> (n1 - n2));
		Optional<Integer> min2 = list.stream().min((n1, n2) -> (n2 - n1));
		System.out.println("min:" + min.get());
		System.out.println("min2:" + min2.get());
		// 映射-规纳
		Optional<Integer> reduce = list.stream().map(n -> n * n).reduce(Integer::sum);
		System.out.println(reduce.get());
		Map<Integer, Long> collect = list.stream()
				.collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
		// 分组
		MapUtils.verbosePrint(System.out, null, collect);
		// 去重－统计
		System.out.println(list.stream().distinct().count());
		new Thread(() -> System.out.println()).start();
		// 可分割迭代器
		Spliterator<Integer> spliterator = list.stream().spliterator();
		while (spliterator.tryAdvance(System.out::println))
			;
		list.stream().spliterator().forEachRemaining(System.out::println);
		// Java8 Date/Time
		System.out.println(Clock.systemUTC().instant());
		System.out.println(Clock.systemUTC().millis());
		// Java8 Base64 内置
		System.out.println(Base64.getEncoder().encodeToString("zhangyonghong".getBytes()));
		System.out.println(new String(Base64.getDecoder().decode("emhhbmd5b25naG9uZw==")));
		// 可以运行js代码的引擎
		System.out.println(
				new ScriptEngineManager().getEngineByName("JavaScript").eval("function f() {return 10;}f()*10;"));
	}

}

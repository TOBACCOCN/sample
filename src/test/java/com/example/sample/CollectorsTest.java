package com.example.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Slf4j
public class CollectorsTest {

    private List<Student> students;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Student {
        private String name;
        private int totalScore;
        private boolean local;
        private Grade grade;
    }

    enum Grade {
        // 对学生按 grade 排序时，Grade 枚举中元素出现的先后顺序即为学生的 grade 的大小顺序（先出现的较小）
        // ONE, TWO, THREE;
        THREE, TWO, ONE;
    }

    @Before
    public void setup() {
        students =  Arrays.asList(
                new Student("刘一", 721, true, Grade.ONE),
                new Student("陈二", 637, true, Grade.TWO),
                new Student("张三", 666, true, Grade.THREE),
                new Student("李四", 531, true, Grade.TWO),
                new Student("王五", 483, false, Grade.THREE),
                new Student("赵六", 367, true, Grade.THREE),
                new Student("孙七", 499, false, Grade.ONE));
        students.sort(Comparator.comparing(Student::getTotalScore).reversed());
    }

    @Test
    public void averagingDouble() {
        Double averageTotalScore = students.stream().collect(Collectors.averagingDouble(Student::getTotalScore));
        log.info(">>>>> averageTotalScore: [{}]", averageTotalScore);
    }

    @Test
    public void collectingAndThen() {
        List<Student> unmodifiedList = students.stream().collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        // unmodifiedList.remove(0);   // 报错
        log.info(">>>>> unmodifiedList: [{}]", unmodifiedList);
    }

    @Test
    public void groupingBy() {
        // 1 groupingBy(Function)
        Map<Grade, List<Student>> grade2StudentsMap = students.stream().collect(Collectors.groupingBy(Student::getGrade));
        log.info(">>>>> grade2StudentsMap: [{}]", grade2StudentsMap);

        // 2 groupingBy(Function, Collector)
        Map<Grade, Long> grade2StudentsCountMap = students.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        log.info(">>>>> grade2StudentsCountMap: [{}]", grade2StudentsCountMap);

        // 3 groupingBy(Function, Supplier, Collector)
        TreeMap<Grade, Double> grade2AverageScoreMap = students.stream()
                .collect(Collectors.groupingBy(Student::getGrade, TreeMap::new, Collectors.averagingDouble(Student::getTotalScore)));
        log.info(">>>>> grade2AverageScoreMap: [{}]", grade2AverageScoreMap);
    }

    @Test
    public void joining() {
        // 1 joining
        String joinedNames = students.stream().map(Student::getName).collect(Collectors.joining());
        log.info(">>>>> joinedNames: [{}]", joinedNames);

        // 2 joining(delimiter)
        joinedNames = students.stream().map(Student::getName).collect(Collectors.joining(", "));
        log.info(">>>>> joinedNames: [{}]", joinedNames);

        // 3 joining(delimiter, prefix, suffix)
        joinedNames = students.stream().map(Student::getName).collect(Collectors.joining(", ", "所有学生:[", "]"));
        log.info(">>>>> joinedNames: [{}]", joinedNames);
    }

    @Test
    public void mapping() {
        String joinedNames = students.stream().collect(Collectors.mapping(Student::getName, Collectors.joining(", ")));       // 建议用下面的 stream().map().collect() 代替
        // joinedNames = students.stream().map(Student::getName).collect(Collectors.joining());
        log.info(">>>>> joinedNames: [{}]", joinedNames);
    }

    @Test
    public void maxByMinBy() {
        // 1 maxBy
        Optional<Student> maxByTotalScoreStudent = students.stream().collect(Collectors.maxBy(Comparator.comparing(Student::getTotalScore)));       // 建议用下面的 stream().max() 代替
        // Optional<Student> maxByTotalScoreStudent = students.stream().max(Comparator.comparing(Student::getTotalScore));
        maxByTotalScoreStudent.ifPresent(o -> log.info(">>>>> maxByTotalScoreStudent: [{}]", o));

        // 2 minBy
        Optional<Student> mimByTotalScoreStudent = students.stream().collect(Collectors.minBy(Comparator.comparing(Student::getTotalScore)));       // 建议用下面的 stream().min() 代替
        // Optional<Student> mimByTotalScoreStudent = students.stream().min(Comparator.comparing(Student::getTotalScore));
        mimByTotalScoreStudent.ifPresent(o -> log.info(">>>>> mimByTotalScoreStudent: [{}]", o));
    }

    @Test
    public void partitioningBy() {
        // 1 partitioningBy(Function)
        Map<Boolean, List<Student>> isLocal2StudentsMap = students.stream().collect(Collectors.partitioningBy(Student::isLocal));
        log.info(">>>>> isLocal2StudentsMap: [{}]", isLocal2StudentsMap);

        // 2 partitionBy(Function, Collector)
        Map<Boolean, List<String>> isLocal2StudentNamesMap = students.stream()
                .collect(Collectors.partitioningBy(Student::isLocal, Collectors.mapping(Student::getName, Collectors.toList())));
    }

    @Test
    public void reducing() {
        // 1 reducing(BinaryOperator)
        Optional<Student> maxTotalScoreStudent = students.stream()
                .collect(Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Student::getTotalScore))));        // 建议将 reducing 的参数直接传给 stream() 的 reduce()
        // Optional<Student> maxTotalScoreStudent = students.stream()
        //         .reduce(BinaryOperator.maxBy(Comparator.comparing(Student::getTotalScore)));
        maxTotalScoreStudent.ifPresent(v -> log.info(">>>>> maxTotalScoreStudent: [{}]", v));
        // 这里的 reducing 无法用 stream().reduce() 代替
        Map<Grade, Optional<Student>> grade2MaxScoreStudentOptionalMap = students.stream()
                .collect(Collectors.groupingBy(Student::getGrade, Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Student::getTotalScore)))));
        log.info(">>>>> grade2MaxScoreStudentOptionalMap: [{}]", grade2MaxScoreStudentOptionalMap);

        // 2 reducing(T, BinaryOperator<T>)
        Map<Grade, Student> grade2MaxTotalScoreStudentMap = students.stream().collect(Collectors.groupingBy(Student::getGrade,
                Collectors.reducing(new Student("周八", 688, false, Grade.ONE),
                        BinaryOperator.maxBy(Comparator.comparing(Student::getTotalScore)))));
        log.info(">>>>> grade2MaxTotalScoreStudentMap: [{}]", grade2MaxTotalScoreStudentMap);

        // 3 reducing(U, Function<T, U>, BinaryOperator<U>)
        Map<Grade, Integer> grade2MaxTotalScoreMap = students.stream().collect(Collectors.groupingBy(Student::getGrade,
                Collectors.reducing(0, Student::getTotalScore,
                        BinaryOperator.maxBy(Comparator.comparing(Integer::intValue)))));
        log.info(">>>>> grade2MaxTotalScoreMap: [{}]", grade2MaxTotalScoreMap);
    }

    @Test
    public void summarizing() {
        DoubleSummaryStatistics doubleSummaryStatistics = students.stream().collect(Collectors.summarizingDouble(Student::getTotalScore));
        log.info(">>>>> doubleSummaryStatistics: [{}]", doubleSummaryStatistics);
        IntSummaryStatistics intSummaryStatistics = students.stream().collect(Collectors.summarizingInt(Student::getTotalScore));
        log.info(">>>>> intSummaryStatistics: [{}]", intSummaryStatistics);
        LongSummaryStatistics longSummaryStatistics = students.stream().collect(Collectors.summarizingLong(Student::getTotalScore));
        log.info(">>>>> longSummaryStatistics: [{}]", longSummaryStatistics);
    }

    @Test
    public void summing() {
        Double summingDouble = students.stream().collect(Collectors.summingDouble(Student::getTotalScore));
        log.info(">>>>> summingDouble: [{}]", summingDouble);
        Integer summingInt = students.stream().collect(Collectors.summingInt(Student::getTotalScore));
        log.info(">>>>> summingInt: [{}]", summingInt);
        Long summingLong = students.stream().collect(Collectors.summingLong(Student::getTotalScore));
        log.info(">>>>> summingLong: [{}]", summingLong);
    }

    @Test
    public void toCollection() {
        ArrayList<Student> collection = students.stream().filter(Student::isLocal).collect(Collectors.toCollection(ArrayList::new));
        log.info(">>>>> collection: [{}]", collection);
        List<Student> list = students.stream().filter(Student::isLocal).collect(Collectors.toList());
        log.info(">>>>> list: [{}]", list);
        Set<Grade> grades = students.stream().map(Student::getGrade).collect(Collectors.toSet());
        log.info(">>>>> grades: [{}]", grades);
    }

    @Test
    public void toMap() {
        // 1 toMap(Function, Function)
        Map<String, Integer> studentName2ScoreMap = students.stream().collect(Collectors.toMap(Student::getName, Student::getTotalScore));
        log.info(">>>>> studentName2ScoreMap: [{}]", studentName2ScoreMap);
        // 2 toMap(Function, Function, BinaryOperator)
        Map<Grade, Integer> grade2StudentsCountMap = students.stream().collect(Collectors.toMap(Student::getGrade, student -> 1, Integer::sum));
        log.info(">>>>> grade2StudentsCountMap: [{}]", grade2StudentsCountMap);
        // 3 toMap(Function, Function, BinaryOperator, Supplier)
        Map<Grade, Integer> grade2StudentsCountLinkedMap = students.stream().collect(Collectors.toMap(Student::getGrade, student -> 1, Integer::sum, LinkedHashMap::new));
        log.info(">>>>> grade2StudentsCountLinkedMap: [{}]", grade2StudentsCountLinkedMap);

        // 1 toConcurrentMap(Function, Function)
        studentName2ScoreMap = students.stream().collect(Collectors.toConcurrentMap(Student::getName, Student::getTotalScore));
        log.info(">>>>> studentName2ScoreConcurrentMap: [{}]", studentName2ScoreMap);
        // 2 toConcurrentMap(Function, Function, BinaryOperator)
        grade2StudentsCountMap = students.stream().collect(Collectors.toConcurrentMap(Student::getGrade, student -> 1, Integer::sum));
        log.info(">>>>> grade2StudentsCountConcurrentMap: [{}]", grade2StudentsCountMap);
        // 3 toConcurrentMap(Function, Function, BinaryOperator, Supplier)
        grade2StudentsCountMap = students.stream().collect(Collectors.toConcurrentMap(Student::getGrade, student -> 1, Integer::sum, ConcurrentSkipListMap::new));
        log.info(">>>>> grade2StudentsCountConcurrentSkipListMap: [{}]", grade2StudentsCountMap);
    }

}

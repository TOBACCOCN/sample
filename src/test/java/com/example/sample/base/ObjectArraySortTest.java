package com.example.sample.base;

import com.example.sample.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Function;

@Slf4j
public class ObjectArraySortTest {

    private static User[] generate() {
        User[] users = new User[100000];
        Random random = new Random();
        for (int i = 0; i < users.length; ++i) {
            users[i] = new User();
            int chinese = random.nextInt(150);
            int math = random.nextInt(150);
            int english = random.nextInt(150);
            int integration = random.nextInt(300);
            int total = chinese + math + english + integration;
            users[i].setChineseScore(chinese);
            users[i].setMathScore(math);
            users[i].setEnglishScore(english);
            users[i].setIntegrationScore(integration);
            users[i].setTotalScore(total);
            System.out.println(chinese + "\t" + math + "\t" + english + "\t" + integration + "\t" + total);
        }
        return users;
    }

    @Test
    public void insertSort() {
    }

    @Test
    public void selectSort() {
    }

    @Test
    public void bubbleSort() {
    }

    @Test
    public void mergeSort() {
        User[] users = generate();
        // User[] origins = Arrays.copyOf(users, users.length);
        // ArrayList<Function<User, Integer>> functions = new ArrayList<>();
        // functions.add(User::getTotalScore);
        // functions.add(User::getChineseScore);
        // functions.add(User::getMathScore);

        Comparator<User> comparator = Comparator.comparingInt(User::getTotalScore)
                .thenComparingInt(User::getChineseScore)
                .thenComparingInt(User::getMathScore)
                .thenComparingInt(User::getEnglishScore)
                .thenComparingInt(User::getIntegrationScore);

        ObjectArraySort.mergeSort(users, null);
        System.out.println("MERGE_SORTED");
        Arrays.stream(users).forEach(user -> System.out.println(user.getChineseScore() + "\t" + user.getMathScore() + "\t"
                + user.getEnglishScore() + "\t" + user.getIntegrationScore() + "\t" + user.getTotalScore()));
        // Arrays.sort(users);

        // functions.add(User::getChineseScore);
        // ObjectArraySort.mergeSort(origins, functions);
        // System.out.println("MERGE_SORTED");
        // Arrays.stream(origins).forEach(user -> System.out.println(user.getChineseScore() + "\t" + user.getMathScore() + "\t"
        //         + user.getEnglishScore() + "\t" + user.getIntegrationScore() + "\t" + user.getTotalScore()));

        // functions.clear();
        // functions.add(User::getChineseScore);
        // ObjectArraySort.mergeSort(users, functions);
        // System.out.println("MERGE_SORTED");
        // Arrays.stream(users).forEach(user -> System.out.println(user.getChineseScore() + "\t" + user.getMathScore() + "\t"
        //         + user.getEnglishScore() + "\t" + user.getIntegrationScore() + "\t" + user.getTotalScore()));
    }

    @Test
    public void quickSort() {
        // User[] users = generate();
        User[] users = {new User(110, 110, 110, 110, 270),
                new User(110, 111, 110, 109, 270),
                new User(110, 110, 111, 109, 270),
                new User(110, 111, 111, 108, 270)};

        Comparator<User> comparator = Comparator.comparingInt(User::getTotalScore)
                .thenComparingInt(User::getChineseScore)
                .thenComparingInt(User::getMathScore)
                .thenComparingInt(User::getEnglishScore)
                .thenComparingInt(User::getIntegrationScore);

        ObjectArraySort.quickSort(users, comparator);
        System.out.println("QUICK_SORTED");
        Arrays.stream(users).forEach(user -> System.out.println(user.getChineseScore() + "\t" + user.getMathScore() + "\t"
                + user.getEnglishScore() + "\t" + user.getIntegrationScore() + "\t" + user.getTotalScore()));
    }
}
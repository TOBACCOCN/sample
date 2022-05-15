package com.example.sample;

import java.util.Scanner;

public class Main {
    // public static void main(String[] args) {
    //     Scanner scanner = new Scanner(System.in);
    //     while (scanner.hasNext()) {
    //         int i = scanner.nextInt();
    //         if (i == 0) {
    //             return;
    //         }
    //         System.out.println(maxBottles(i));
    //     }
    // }
    //
    // private static int maxBottles(int input) {
    //     int drinked = input / 3;
    //     int empty = drinked + input % 3;
    //     if (empty >= 3) {
    //         drinked += maxBottles(empty);
    //     } else if (empty == 2) {
    //         drinked += 1;
    //     }
    //     return drinked;
    // }

    // public static void main(String[] args) {
    //     Scanner scanner = new Scanner(System.in);
    //     int count = scanner.nextInt();
    //     Set<Integer> set = new TreeSet<>();
    //     while (count-- > 0) {
    //         set.add(scanner.nextInt());
    //     }
    //     Stream.of(set).forEach(System.out::println);
    // }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        long result = 0;
        for (int i = 2; i < input.length(); ++i) {
            char c = input.charAt(i);
            double pow = Math.pow(16, input.length() - 1 - i);
            if (c >= 'A' && c <= 'Z') {
                result += pow * (c - 'A' + 10);
            } else if (c >= 'a' && c <= 'z') {
                result += pow * (c - 'a' + 10);
            } else if (c >= '0' && c <= '9') {
                result += pow * (c - '0');
            }
        }
        System.out.println(result);
    }
}

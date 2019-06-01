package com.example.demo.leetcode;

public class Sqrt {
	
	public static void main(String[] args) {
		System.out.println(sqrt(10));
	}
	
    public static int sqrt(int x) {
        long n = x;
        while (n * n > x) {
            n = (n + x / n) >> 1;
        }
        return (int) n;
    }

}

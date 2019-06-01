package com.example.demo.base;

import java.util.LinkedList;
import java.util.List;

public class Solution {
    /**
     * Generate combinations.
     *
     * @param result result list.
     * @param prefix current prefix.
     * @param open parentheses that can be opened.
     * @param close parentheses that needs to be closed.
     */
    public static void combination(List<String> result, String prefix, int open, int close) {
        // If cannot open or close then we are done
        if (open == 0 && close == 0) {
            result.add(prefix);
        } else {
            // If can open parentheses generate combinations from it
            if (open > 0) {
                combination(result, prefix + '(', open - 1, close + 1);
            }
            
            // If can close parentheses generate combinations from it
            if (close > 0) {
                combination(result, prefix + ')', open, close - 1);
            }
        }
    }
    
    public static List<String> generateParenthesis(int n) {
        List<String> result = new LinkedList<>();
        
        // Generate combinations of opening n parantheses
        combination(result, "", n, 0);
        
        return result;
    }
    
    public static void main(String[] args) {
    	long begin = System.currentTimeMillis();
    	List<String> list = generateParenthesis(15);
    	long end = System.currentTimeMillis();
//    	for (String string : list) {
//			System.out.println(string);
//		}
    	System.out.println(list.size());
    	long cost = end - begin;
		System.out.println("用时:"+cost+"毫秒");
	}
}

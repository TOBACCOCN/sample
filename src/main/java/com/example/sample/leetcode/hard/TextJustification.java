package com.example.sample.leetcode.hard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given an array of words and a width maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.
 * You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.
 * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
 * For the last line of text, it should be left justified and no extra space is inserted between words.
 * Note:
 * A word is defined as a character sequence consisting of non-space characters only.
 * Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
 * The input array words contains at least one word.
 * Example 1:
 * Input:
 * words = ["This", "is", "an", "example", "of", "text", "justification."]
 * maxWidth = 16
 * Output:
 * [
 * "This    is    an",
 * "example  of text",
 * "justification.  "
 * ]
 * Example 2:
 * Input:
 * words = ["What","must","be","acknowledgment","shall","be"]
 * maxWidth = 16
 * Output:
 * [
 * "What   must   be",
 * "acknowledgment  ",
 * "shall be        "
 * ]
 * Explanation: Note that the last line is "shall be    " instead of "shall     be",
 * because the last line must be left-justified instead of fully-justified.
 * Note that the second line is also left-justified becase it contains only one word.
 * Example 3:
 * Input:
 * words = ["Science","is","what","we","understand","well","enough","to","explain",
 * "to","a","computer.","Art","is","everything","else","we","do"]
 * maxWidth = 20
 * Output:
 * [
 * "Science  is  what we",
 * "understand      well",
 * "enough to explain to",
 * "a  computer.  Art is",
 * "everything  else  we",
 * "do                  "
 * ]
 *
 * @author zhangyonghong
 * @date 2020.3.14
 */
@Slf4j
public class TextJustification {

    public List<String> textJustification(List<String> words, int maxWidth) {
        List<String> list = new ArrayList<>();
        int len = words.size();
        int i = 0;
        while (i < len) {
            StringBuilder line = new StringBuilder();
            String word = "";
            int start = i;
            while (i < len && line.length() < maxWidth) {
                word = words.get(i);
                line.append(word).append(" ");
                ++i;
            }

            if (i == len) {
                list.add(line.substring(0, line.length() - 1));
                continue;
            }

            String l;
            if (line.length() - 1 <= maxWidth) {
                l = line.substring(0, line.length() - 1);
            } else {
                l = line.substring(0, line.length() - word.length() - 2);
                --i;
            }

            // 多余空格
            int leftBlank = maxWidth - l.length();
            if (i - start > 1 && leftBlank > 0) {
                // 把多余空格均匀地插入到单词之间
                int n = leftBlank / (i - start - 1);
                String blank = " ";
                if (n > 0) {
                    blank = String.join("", Collections.nCopies(1 + n, " "));
                    l = l.replaceAll(" ", blank);
                }

                // 如果多余空格不能平分的话，那就从左开始依次多插一个空格
                n = leftBlank % (i - start - 1);
                int t = 0;
                while (n > 0) {
                    int index = l.indexOf(blank, t);
                    l = l.substring(0, index) + " " + l.substring(index);
                    t += index + blank.length() + 2;
                    --n;
                }
            }
            list.add(l);
        }
        return list;
    }

    @Test
    public void textJustification() {
        List<String> words = Arrays.asList("Science", "is", "what", "we", "understand", "well", "enough", "to",
                "explain", "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do");
        int maxWidth = 15;
        log.info(">>>>> RESULT:[{}]", textJustification(words, maxWidth));
    }

}

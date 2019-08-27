package com.wangff.learning;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by wangff on 2018/11/27.
 */
@Slf4j
public class ACM {

    public static void main(String[] args) {
        ACM test = new ACM();
        reverseWord("what your name");
    }
    /**
     * 8括号是否匹配
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Map<Character, Character> charMap = new HashMap<>();
        charMap.put('}', '{');
        charMap.put(']', '[');
        charMap.put(')', '(');
        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            if (charMap.containsValue(s.charAt(i))) {//左括弧 入栈
                stack.push(s.charAt(i));
            }
            if (charMap.containsKey(s.charAt(i))) {//右括弧 栈空或者与栈顶元素不匹配时返回false。匹配时,出栈
                if(stack.isEmpty()||stack.peek()!=charMap.get(s.charAt(i))){
                    return false;
                }else{
                    stack.pop();
                }
            }
        }
        return true;
    }
    /**
     * 7字符串反转单词顺序不变
     * @param str
     * @return
     */
    public static String reverseWord(String str) {
        char[] arr = str.toCharArray();
        //遍历每一个单词
        for (int i = 0; i < arr.length; i++) {
            int left = i;//单词左索引
            while (i<arr.length&&arr[i]!=' '){//未见空格指针右移，遇见空格结束循环。即扫描一个单词
                i++;
            }
            int right = i - 1;//单词右索引
            while (left<right){//反转此单词
                //高低位交换
                char temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }
        String rlt = new String(arr);
        log.info("rlt=={}",rlt);
        return new String(arr);
    }
    /**
     * 6字符串反转 双指针
     * @param str
     * @return
     */
    public static String reverse(String str) {
        char[] arr = str.toCharArray();
        int left = 0;
        int right = arr.length - 1;
        while (left<right){
            //高低位交换
            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
        String rlt = new String(arr);
        log.info("rlt=={}",rlt);
        return rlt;
    }

    /**
     * 5冒泡排序 升序
     * @param arr
     * @return
     */
    public static int[] maopoAsc(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length-i-1; j++) {
                if (arr[j] > arr[j + 1]) {//相邻两两比较 交换顺序
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }
    /**
     * 4递推斐波那契数列
     * 0,1,1,2,3,5...
     * fn=fn1+fn2
     * @param n
     * @return
     */
    public static int fibonaqi(int n){
        if (n == 0||n==1) {
            return n;
        }
        int f0 = 0;
        int f1 = 1;
        int f2 = 0;
        for (int i = 2; i <=n ; i++) {
            f2 = f0 + f1;
            f0= f1;
            f1 = f2;
        }
        return f2;
    }

    /**
     * 3二分查找 有时返回数组索引，无时返回-1
     * @param arr 有序递增数组
     * @param num 目标值
     * @return
     */
    public static int getIndex(int[] arr, int num) {
        // 定义变量，表示查找数组范围的最左侧，先从0索引开始
        int left = 0;
        // 定义变量，表示查找数组范围的最右侧，先从最大索引开始
        int right = arr.length - 1;
        // 定义变量，表示查找范围的中间值
        int mid;
        while (left <= right) {
            // 中间索引 = (左侧  + 右侧) / 2
             mid = (left + right) / 2;
            // 为了提高效率，我们可以用位运算代替除以运算
            if (arr[mid] > num) {
                //如果中间元素大于要查找元素，则在中间元素的左侧去找正确元素，最右侧变为mid - 1
                right = mid - 1;
            } else if (arr[mid] < num) {
                //如果中间元素小于要查找元素，则在中间元素的右侧去找正确元素，最左侧变为mid + 1
                left = mid + 1;
            } else {
                // 如果不大不小，那么就正好是找到了，返回找到的索引
                return mid;
            }
        }
        // 当查找范围的最左侧和最右侧重叠后还没有找到元素，则返回-1表示没有找到
        return -1;
    }

    /**
     * 2最大公约数 辗转相除递归
     * @param a
     * @param b
     * @return
     */
    public int f(int a,int b)  {
        if (a%b==0) {
            return b;
        }else{
            return f(b,a%b);
        }
    }

    /**
     * 1返回子串在原字符串的第一个索引
     * @param source
     * @param target
     * @return
     */
    public int indexOf(String source, String target) {
        char[] a = source.toCharArray();
        char[] b = target.toCharArray();
        for (int i = 0; i < a.length; i++) {
            if(a[i]==b[0]){
                for (int j = 0; j < b.length; j++) {
                    if (i+j>=a.length|| a[i + j]!=b[j]) {
                        return -1;
                    }
                }
                return i;
            }
        }
        return -1;
    }
}

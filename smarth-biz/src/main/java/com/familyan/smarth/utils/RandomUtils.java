package com.familyan.smarth.utils;

import java.util.Random;

/**
 * Created by Administrator on 2015/8/29 0029.
 */
public class RandomUtils {

    /**
     * 生成固定位数的随机数字，每一位数字范围是0-9
     * @param numCount 位数
     * @return
     */
    public static String generateRandomNumber(int numCount){
        if(numCount <= 0 || numCount > 20){
            throw new RuntimeException("numCount must between 1 and 20");
        }
        StringBuilder sbRandomNumber = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<numCount;i++){
            sbRandomNumber.append(random.nextInt(10));
        }
        return sbRandomNumber.toString();
    }

    /**
     * 根据提供的字典库，生成固定位数的随机串。
     * @param dictionary 字典库
     * @param numCount 位数
     * @return
     */
    public static String generateRandomString(String dictionary, int numCount){
        if(dictionary == null || dictionary.length() == 0){
            throw new RuntimeException("dictionary is empty");
        }
        if(numCount <= 0 || numCount > 20){
            throw new RuntimeException("numCount must between 1 and 20");
        }
        StringBuilder sbRandom = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<numCount;i++){
            int index = random.nextInt(dictionary.length());
            char c = dictionary.charAt(index);
            sbRandom.append(c);
        }
        return sbRandom.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateRandomNumber(6));
        System.out.println(generateRandomString("abcdefghijklmnopqrstuvwxyz0123456789", 6));
    }
}

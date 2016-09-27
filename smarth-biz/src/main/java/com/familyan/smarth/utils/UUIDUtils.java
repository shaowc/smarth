package com.familyan.smarth.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
public class UUIDUtils {

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     */
    public static String uuid2() {
        return UUID.randomUUID().toString();
    }
}

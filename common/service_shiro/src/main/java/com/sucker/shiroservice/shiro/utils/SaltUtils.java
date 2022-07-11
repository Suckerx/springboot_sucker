package com.sucker.shiroservice.shiro.utils;

import java.util.Random;

public class SaltUtils {
    /**
     * 生成salt的静态方法，取n位
     * @param n
     * @return
     */
    public static String getSalt(int n){
        //随机字符数组
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()".toCharArray();
        //用于返回结果
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            //每次随机取一个字符再拼接
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String salt = getSalt(4);
        System.out.println(salt);
    }
}

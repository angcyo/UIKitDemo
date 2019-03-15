package com.angcyo.uikitdemo.java;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/03/15
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class Java {
    public static void main() {
        String s1 = "Hello";
        String s2 = new String("Hello");
        String s3 = "He" + "llo";

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s2 == s3);
    }
}

package com.angcyo.uikitdemo.java;

import com.angcyo.http.Json;
import com.angcyo.lib.L;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/03/15
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class Java {
    public static void main() {
        L.e("Java ......... ");
        String s1 = "Hello";
        String s2 = new String("Hello");
        String s3 = "He" + "llo";

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s2 == s3);

        String nullStrValue = null;
        Number nullNumValue = null;

        L.i(Json.json()
                .add("Key", "key")
                .add("Key2", nullStrValue)
                .add("Key3", 1)
                .groupJson("newJson")
                .add("json1", "json1")
                .groupArray("newArray")
                .add("nullNum", nullNumValue)
                .add("nullStr", nullStrValue)
                .add("ak1", 1)
                .add("ak2", "2")
                .endGroup()
                .endGroup()
                .get());

        L.e("Java ......... end");
    }
}

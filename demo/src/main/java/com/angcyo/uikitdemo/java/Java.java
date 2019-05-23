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
                .add("Key4", 2)
                .addJson("newJson")
                .add("json1", "json1")
                .addArray("newArray")
                .add(nullNumValue)
                .add(nullStrValue)
                .add(1)
                .add("2")
                .endAdd()
                .endAdd()
                .get());

        L.i(Json.json().add("key", "value").get());
        L.i(Json.json().add("k", "v'").addJson("obj").add("k", "v").endAdd().get());
        L.i(Json.json().add("k", "v'").addArray("array").add("k", "v").endAdd().get());
        L.i(Json.json()
                .add("k", "v'").add("k2", "v2")
                .addArray("array")
                .addJson().add("k", "v").add("k2", "v2")
                .endAll()
                .get());
        L.i(Json.json().add("k", 1)
                .add("v1", 1)
                .add("v2", 2)
                .add("v3", 3L)
                .add("k2", 1f).add("k3", 2d).add("k4", 3.00000000000000d)
                .addArray("array")
                .addJson().add("k", "v").add("k2", "v2").endAdd()
                .addJson().add("k", "v").add("k2", "v2").endAdd()
                .get());

        L.e(" ......... ");

        L.i(Json.array()
                .addJson().add("k", "v").add("k2", "v2").endAdd()
                .addJson().add("k", "v").add("k2", "v2")
                .addArray("array").addJson().add("k", "v").add("k2", "v2").endAdd()
                .get());

        L.e("Java ......... end");
    }
}

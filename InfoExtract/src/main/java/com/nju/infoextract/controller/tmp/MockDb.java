package com.nju.infoextract.controller.tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/11/20
 */
public class MockDb {
    public static List<MockUser> users = new ArrayList<>();

    public static Map<String, String> tokens = new HashMap<>();

    /**
     * 存储用户名-案由的对应关系
     */
    public static Map<String, String> aystore = new HashMap<>();
}

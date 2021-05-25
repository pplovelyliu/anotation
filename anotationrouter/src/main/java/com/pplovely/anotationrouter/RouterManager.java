package com.pplovely.anotationrouter;

import java.util.HashMap;

public class RouterManager {
    public static final String INIT_CLASS = "com.pplovely.anotationroter.TestRouterInit";
    public static final String INIT_METHOD = "init";

    private HashMap<String, String> map = new HashMap<>();

    private static final class Host {
        private static final RouterManager instance = new RouterManager();
    }

    private RouterManager() {
    }

    public void init() {
        try {
            Class.forName(INIT_CLASS).getMethod(INIT_METHOD).invoke(null);//调用动态生成的文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RouterManager getInstance() {
        return Host.instance;
    }

    public void register(String key, String uri) {
        if (key != null && uri != null) {
            map.put(key, uri);
        }
    }

    public void showAllActivity() {
        System.out.println("RouterManager:" + map.toString());
    }
}

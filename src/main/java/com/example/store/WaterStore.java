package com.example.store;

import com.example.seniorcare.model.WaterPrefs;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WaterStore {
    private static final Map<String, WaterPrefs> prefs = new ConcurrentHashMap<>();

    public static void putPrefs(String user, WaterPrefs pref) {
        prefs.put(user, pref);
    }

    public static WaterPrefs getPrefs(String user) {
        return prefs.get(user);
    }

    public static Map<String, WaterPrefs> all() {
        return prefs;
    }
}

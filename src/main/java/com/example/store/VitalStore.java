package com.example.store;

import com.example.seniorcare.model.VitalSign;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class VitalStore {
    private static final Map<String, List<VitalSign>> store = new ConcurrentHashMap<>();

    public static void addVital(String user, VitalSign vital) {
        store.computeIfAbsent(user, k -> Collections.synchronizedList(new ArrayList<>())).add(vital);
    }

    public static List<VitalSign> getVitals(String user) {
        return store.getOrDefault(user, new ArrayList<>());
    }
}

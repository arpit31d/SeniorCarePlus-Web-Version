package com.example.store;

import com.example.seniorcare.model.MedicationPlan;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MedicationStore {
    private static final Map<String, List<MedicationPlan>> store = new ConcurrentHashMap<>();
    private static final Set<String> notified = Collections.synchronizedSet(new HashSet<>());

    public static void addPlan(String user, MedicationPlan plan) {
        store.computeIfAbsent(user, k -> Collections.synchronizedList(new ArrayList<>())).add(plan);
    }

    public static List<MedicationPlan> getPlans(String user) {
        return store.getOrDefault(user, new ArrayList<>());
    }

    public static boolean markNotified(String user, MedicationPlan p) {
        String key = user + "|" + p.name + "|" + p.getRefillReminderDate();
        return notified.add(key);
    }

    public static Map<String, List<MedicationPlan>> allPlans() {
        return store;
    }
}

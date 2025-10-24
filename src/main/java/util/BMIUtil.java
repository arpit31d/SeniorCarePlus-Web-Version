package util;

public class BMIUtil {
    public static float calculateBMI(float heightCm, float weightKg) {
        float heightM = heightCm / 100.0f;
        return weightKg / (heightM * heightM);
    }

    public static String healthStatus(float bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Normal weight";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }
}

package util;

public class GoogleMeetUtil {
    public static String generateMeetLink() {
        
        return "https://meet.google.com/your-custom-link-" + System.currentTimeMillis();
    }
}

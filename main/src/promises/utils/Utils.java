package promises.utils;

public class Utils {
    public static double round(double value) {
        return Math.round(value);
    }

    public static void randomDelay() {
        int random = (int)(100 * Math.random() + 5);
        try {
            Thread.sleep(random * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

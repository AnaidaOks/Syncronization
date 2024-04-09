package myThread;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static int maxCount = 0;
    public static int maxFreq = 0;

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String genStr = generateRoute("RLRFR", 100);

                long rCount = Arrays.stream(genStr.split("")).
                        filter(ch -> "R".equals(ch)).count();
                setSizeToFreq((int) rCount);
            }).start();
        }

        System.out.println("Самое частое количество повторений " + maxCount + " (встретилось " + maxFreq + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> freq : sizeToFreq.entrySet()) {
            System.out.println("- " + freq.getKey() + " (" + freq.getValue() + " раз)");

        }
    }

    public static synchronized void setSizeToFreq(int count) {
        if (sizeToFreq.containsKey(count)) {
            Integer freq = sizeToFreq.get(count) + 1;
            if (maxFreq < freq) {
                maxFreq = freq;
                maxCount = count;
            }
            sizeToFreq.put(count, freq);
        } else {
            sizeToFreq.put(count, 1);
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
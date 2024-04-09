package myThread;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static int maxCount = 0;
    public static int maxFreq = 0;

    public static void main(String[] args) throws InterruptedException {

//        Thread thread = new Thread(() -> {
//
//
//            while (!Thread.interrupted()) {
//                synchronized (sizeToFreq) {
//                    try {
//                        sizeToFreq.wait();
//                        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
//                            if (maxFreq < entry.getValue()) {
//                                maxCount = entry.getKey();
//                                maxFreq = entry.getValue();
//                                break;
//                            }
//                        }
//                        System.out.println(System.nanoTime() + ": Самое частое количество повторений на тек. момент " + maxCount + " (встретилось " + maxFreq + " раз)");
//
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        });
//
//        thread.start();

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {

                String genStr = generateRoute("RLRFR", 100);

                int rCount = (int) Arrays.stream(genStr.split("")).
                        filter("R"::equals).count();
                synchronized (sizeToFreq) {
                    setSizeToFreq(rCount);
                    //sizeToFreq.notify();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
        //thread.join();
        //thread.interrupt();
        synchronized (sizeToFreq) {
            System.out.println("Самое частое количество повторений " + maxCount + " (встретилось " + maxFreq + " раз)");
            System.out.println("Другие размеры:");
            for (Map.Entry<Integer, Integer> freq : sizeToFreq.entrySet()) {
                System.out.println("- " + freq.getKey() + " (" + freq.getValue() + " раз)");

            }
        }
    }
    
    public static void setSizeToFreq(int count) {
        synchronized (sizeToFreq) {
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
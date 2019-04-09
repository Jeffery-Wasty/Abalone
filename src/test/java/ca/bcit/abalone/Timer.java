package ca.bcit.abalone;

public class Timer {

    public static void time(Runnable runnable) {
        long time = System.currentTimeMillis();

        runnable.run();

        time = System.currentTimeMillis() - time;
        System.out.println("Time spent: " + time + "ms");
    }

}

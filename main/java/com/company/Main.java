package com.company;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {

    private static Life life = new Life();

    // entry point for program
    public static void main(String[] args) {
        final int[][] currentGeneration = new int[25][25];
        glider(currentGeneration);

        // initialize scheduler
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleWithFixedDelay(() -> {
                    output(currentGeneration);
                    int[][] newGeneration = life.nextGeneration(currentGeneration);

                    for (int i = 0; i < newGeneration.length; i++) {
                        System.arraycopy(newGeneration[i], 0, currentGeneration[i], 0, newGeneration[i].length);
                    }
                },
                1, 3, TimeUnit.SECONDS);

        scheduler.schedule(() -> { scheduledFuture.cancel(true); }, 1000, TimeUnit.SECONDS);
    }

    private static void output(int[][] newGeneration) {
        for (int i = 0; i < newGeneration.length; i++) {
            for (int j = 0; j < newGeneration.length; j++) {
                System.out.print(newGeneration[i][j]+"   ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    private static void glider(int[][] life) {
        int centre = life.length/2;
        life[centre][centre] = 1;
        life[centre][centre + 1] = 1;
        life[centre][centre + 2] = 1;
        life[centre + 1][centre + 2] = 1;
        life[centre + 2][centre + 1] = 1;
    }
}

package fitnesstracker;

import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SHAFI
 */
public class Running implements Runnable{
    
    private double distance; // Distance covered in kilometers
    private long startTime; // Start time in milliseconds
    private volatile boolean isRunning; // Flag to indicate if the workout is ongoing
    public Running(double distance) {
        this.distance = distance;
        this.startTime = 0;
        this.isRunning = false;
    }
    public void startWorkout() {
        
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
        System.out.println("Workout started!");

        // Start a new thread for real-time tracking
        Thread trackingThread = new Thread(this);
        trackingThread.start();
        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type 's' to end the workout:");
            while (isRunning) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("s")) {
                    endWorkout();
                }
            }
        });
        inputThread.start();
    }

    public void endWorkout() {
        if (!this.isRunning) {
            System.out.println("Workout is not running.");
            return;
        }
        this.isRunning = false;
        System.out.println("Workout ended!");
        trackDuration(); // Display final duration
    }
    public void trackDuration() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - this.startTime;

        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / (1000 * 60)) % 60;
        long hours = (elapsedTime / (1000 * 60 * 60)) % 24;

        System.out.println("Elapsed time: " + hours + "h " + minutes + "m " + seconds + "s");
    }
    @Override
    public void run() {
        while (isRunning) {
            try {
                // Wait for 1 second before updating the display
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Print the elapsed time in real time
            trackDuration();
        }
    }
}

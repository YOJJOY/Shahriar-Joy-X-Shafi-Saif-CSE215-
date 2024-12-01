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
public class Running implements Runnable {

    private double distance; // Target distance in kilometers
    private long startTime; // Start time in milliseconds
    private volatile boolean isRunning; // Flag to indicate if the workout is ongoing
    private double currentDistance; // Distance covered in kilometers
    private final double averageSpeed = 8.0; // Average speed in km/h for a beginner runner
    private RunningUpdateCallback callback; // Interface for updating the GUI

    public Running(double distance, RunningUpdateCallback callback) {
        this.distance = distance;
        this.startTime = 0;
        this.isRunning = false;
        this.currentDistance = 0.0;
        this.callback = callback;
    }

    public void startWorkout() {
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
        System.out.println("Workout started!");

        // Start the workout tracking thread
        Thread trackingThread = new Thread(this);
        trackingThread.start();
    }

    public void endWorkout() {
        if (!this.isRunning) {
            System.out.println("Workout is not running.");
            return;
        }
        this.isRunning = false;
        System.out.println("Workout ended!");
        trackDuration(); // Display final duration
        if (callback != null) {
            callback.onWorkoutEnded(currentDistance);
        }
    }

    public void trackDuration() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - this.startTime;

        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / (1000 * 60)) % 60;
        long hours = (elapsedTime / (1000 * 60 * 60)) % 24;

        String timeElapsed = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
        if (callback != null) {
            callback.onUpdateElapsedTime(timeElapsed);
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(1000); // Update every second
                trackDuration();
                updateDistance();
                if (currentDistance >= distance) {
                    endWorkout();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateDistance() {
        long elapsedTime = System.currentTimeMillis() - this.startTime; // milliseconds
        double elapsedHours = elapsedTime / (1000.0 * 60.0 * 60.0); // convert to hours
        this.currentDistance = elapsedHours * averageSpeed;
        if (callback != null) {
            callback.onUpdateDistance(currentDistance);
        }
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}

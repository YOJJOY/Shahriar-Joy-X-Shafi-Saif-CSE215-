/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fitnesstracker;

/**
 *
 * @author SHAFI
 */
public interface RunningUpdateCallback {
    void onUpdateElapsedTime(String elapsedTime);
    void onUpdateDistance(double distanceCovered);
    void onWorkoutEnded(double totalDistance);
}

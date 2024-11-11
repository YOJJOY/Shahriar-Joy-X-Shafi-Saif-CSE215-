/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SHAFI
 */
public class Running extends FitnessGoals{
    private double targetDistance;
    Running(){
        targetDistance = 0.0;
        
    }
    Running(double targetDistance){
        this.targetDistance = targetDistance;
    }
    public void setTargetDistance(double newDistance){
        this.targetDistance = newDistance;
    }
    public double getTargetDistance(){
        return targetDistance;
    }
}

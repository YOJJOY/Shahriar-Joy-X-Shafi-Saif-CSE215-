package fitnesstracker;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SHAFI
 */
public class WeightLoss extends FitnessGoals {
    private double currentWeight;
    private double targetWeight;
    WeightLoss(){
        super();
        currentWeight = 0.0;
        targetWeight = 0.0;
    }
    WeightLoss(double currentWeight, double targetWeight){
        this.currentWeight = currentWeight;
        this.targetWeight = targetWeight;
        
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }
   
    public double WeightDifference(){
        if(currentWeight >= targetWeight){
            return currentWeight-targetWeight;
        }
        else return 0;
    }

   
    
    
    
}

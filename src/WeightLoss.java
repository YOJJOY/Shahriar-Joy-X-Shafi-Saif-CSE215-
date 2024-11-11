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
    WeightLoss(String name,double currentWeight, double targetWeight){
        super(name);
        this.currentWeight = currentWeight;
        this.targetWeight = targetWeight;
        
    }
    public double getCurrentWeight(){
        return currentWeight;
    }
    public void setCurrentWeigth(double newWeight){
        currentWeight = newWeight;
    }
    public double getTargetWeight(){
        return targetWeight;
    }
    public void setTargetWeigth(double newWeight){
        targetWeight = newWeight;
    }
    public double WeightDifference(){
        if(currentWeight >= targetWeight){
            return targetWeight-currentWeight;
        }
        else return 0;
    }
    
    
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SHAFI
 */
public class WeightGain extends WeightLoss {
    WeightGain(){
        super();
    }
    WeightGain(String name,double currentWeight, double targetWeight){
        super(name, currentWeight, targetWeight);   
    }
    public double WeightDifference(){
        if(getCurrentWeight() <= getTargetWeight()){
            return getCurrentWeight()-getTargetWeight();
        }
        else return 0;
    }
}

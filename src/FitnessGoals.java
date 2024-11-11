/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SHAFI
 */
public class FitnessGoals {
    private String name;
    FitnessGoals(){
        this("Unkown");
    }
    FitnessGoals(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setName(String newName){
        name = newName;
    }
    //need to create to string functions for all the classes
}


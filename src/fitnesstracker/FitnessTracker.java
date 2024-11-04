/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fitnesstracker;
import java.util.*;
public class FitnessTracker {
    public static void HomeScreen(){
        int UserInput;
        System.out.println("1. Login");
        System.out.println("2. Sign up");
        Scanner s = new Scanner(System.in);
        UserInput = s.nextInt();
        if(UserInput == 1){
            Login();
        }
        else if(UserInput == 2){
            SignUp();
        }
        else {
            System.out.println("Error");
            HomeScreen();
        }
    }
    public static void Login(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Your Name: ");
        String Name = s.nextLine();
        //need to compare name with the one in file here
        System.out.println("Enter Your Password: ");
        String Password = s.nextLine();
        //need to compare password with the one in file here
        //need to add function to mainhub here
        //need to access userID here
        MainHub(Name);
    }
    public static void SignUp(){
       Scanner s = new Scanner(System.in);
        System.out.println("Enter Your Name: ");
        String Name = s.next();
        //need to add name to file here 
        System.out.println("Enter Your Password: ");
        String Password = s.next();
        //need to add password to file here 
        //need to assign the user and ID here for better tracking within files
        HomeScreen();
    }
    public static void MainHub(String Name/*need to pass userID here*/){
       System.out.println("Welcome Back "+Name);
    }
    public static void main(String[] args) {
     HomeScreen();
    }
    
    
}

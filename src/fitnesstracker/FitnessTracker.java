/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fitnesstracker;
import java.util.*;
public class FitnessTracker {
    

    public static void HomeScreen(List<UserClass> users){
        int UserInput;
        System.out.println("1. Login");
        System.out.println("2. Sign up");
        Scanner s = new Scanner(System.in);
        UserInput = s.nextInt();
        if(UserInput == 1){
            Login(users);
        }
        else if(UserInput == 2){
            SignUp(users);
            Login(users);
        }
        else {
            System.out.println("Error");
            HomeScreen(users);
        }
    }
    public static void Login(List<UserClass> users){
        
        
        
        Scanner scanner = new Scanner(System.in);
            UserClass loggedInUser = null;
            int attempt = 5;
            while(attempt > 0){
            System.out.print("Enter username: ");
            String inputUsername = scanner.next();
            System.out.print("Enter password: ");
            String inputPassword = scanner.next();
             loggedInUser = LoginSystem.login(users, inputUsername, inputPassword);
            if (loggedInUser!= null) {
                System.out.println("Login successful! Welcome, " + loggedInUser.getName() + "!");
                break; 
            } else {
                attempt --;
                System.out.println("Invalid username or password. "+attempt+" left");
            }
            
            }
            
            System.out.println("What is your target weight");
            double targetWeight = scanner.nextDouble();
            WeightLoss wl = new WeightLoss(loggedInUser.getWeight(), targetWeight);
            System.out.println("Enter your new weight");
            double newWeight = scanner.nextDouble();
            loggedInUser.setWeight(newWeight);
            wl.setCurrentWeight(loggedInUser.getWeight());
            System.out.println("You are "+wl.WeightDifference()+"kgs away from your goal");
            
            /*Running running = new Running(5.0);
            System.out.println("Do you want to start running"+"\n"+"1. yes"+"\n"+"2. no");
            int num = scanner.nextInt();
            if(num == 1){
                running.startWorkout();
            }*/
            
    }
    public static void SignUp(List<UserClass> users){
       Scanner s = new Scanner(System.in);
        System.out.println("Enter Your User Name: ");
        String UserName = s.next();
        System.out.println("Enter Your Password: ");
        String Password = s.next();
        System.out.println("Enter your Name: ");
        String name = s.nextLine();
        System.out.println("Enter your Age: ");
        int age = s.nextInt();
        System.out.println("Enter your height: ");
        double height = s.nextDouble();
        System.out.println("Enter your weight: ");
        double weight = s.nextDouble();
       UserClass user = new UserClass(UserName, Password, name, age, height, weight);
       users.add(user);

    }
    public static void MainHub(String Name/*need to pass userID here*/){
       System.out.println("Welcome Back "+Name);
       //1. Add fitness goals
       //2.tracking progress
       //3. modify goals
    }
    public static void main(String[] args) {
        List<UserClass> users = initializeUsers();
        HomeScreen(users);
    }
     private static List<UserClass> initializeUsers() {
        List<UserClass> users = new ArrayList<>();
        users.add(new UserClass("Alice", "password123","Alice", 13, 189, 180));
        users.add(new UserClass("Bob", "qwerty", "Builder", 89, 200, 89));
        users.add(new UserClass("Charlie", "letmein","Chaplin", 69, 120, 60));
        users.add(new UserClass("Dave", "securepass","Dave", 45, 210, 89));
        users.add(new UserClass("Eve", "123456", "Adam", 80, 190, 80));

        return users;
    }
    
    
}
class LoginSystem {
    public static UserClass login(List<UserClass> users, String inputUsername, String inputPassword) {
        // Check if any user matches the provided credentials
        for (UserClass user : users) {
            if (user.getUsername().equals(inputUsername) && user.getPassword().equals(inputPassword)) {
                System.out.println("Login successful! Welcome, " + user.getUsername() + "!");
                return user;
            }
        }

        System.out.println("Invalid username or password.");
        return null;
    }
    
    
}


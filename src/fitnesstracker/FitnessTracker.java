/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fitnesstracker;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        String UserName = s.nextLine();
        System.out.println("Enter Your Password: ");
        String Password = s.nextLine();
        System.out.println("Enter your Name: ");
        String name = s.nextLine();
        System.out.println("Enter your Age: ");
        int age = s.nextInt();
        System.out.println("Enter your height: ");
        double height = s.nextDouble();
        System.out.println("Enter your weight: ");
        double weight = s.nextDouble();
       UserClass user = new UserClass(UserName, Password, name, age, height, weight);
       addUser(users, user, "UserInfo.txt");
       for(UserClass user1 : users){
            System.out.println("UserName: "+user1.getUsername());
            System.out.println("Password: "+user1.getPassword());
            System.out.println("UserID: "+user1.getUserID());
            System.out.println("Name: "+user1.getName());
            System.out.println("Age: "+user1.getAge());
            System.out.println("Height: "+user1.getHeight());
            System.out.println("Weight: "+user1.getWeight());
            System.out.println("Weight Loss Target: "+user1.getWeightLossTarget());
            System.out.println("Weight Gain Target: "+user1.getWeightGainTarget());
            System.out.println("Running Distance: "+user1.getRunningTaget());
        }

    }
    public static void MainHub(String Name/*need to pass userID here*/){
       System.out.println("Welcome Back "+Name);
       //1. Add fitness goals
       //2.tracking progress
       //3. modify goals
    }
    public static void main(String[] args) {
        List<UserClass> users = readUsersFromFile("UserInfo.txt");
        /*for(UserClass user : users){
            System.out.println("UserName: "+user.getUsername());
            System.out.println("Password: "+user.getPassword());
            System.out.println("UserID: "+user.getUserID());
            System.out.println("Name: "+user.getName());
            System.out.println("Age: "+user.getAge());
            System.out.println("Height: "+user.getHeight());
            System.out.println("Weight: "+user.getWeight());
            System.out.println("Weight Loss Target: "+user.getWeightLossTarget());
            System.out.println("Weight Gain Target: "+user.getWeightGainTarget());
            System.out.println("Running Distance: "+user.getRunningTaget());
        }*/
        HomeScreen(users);
    }
    /* private static List<UserClass> initializeUsers() {
        List<UserClass> users = new ArrayList<>();
        users.add(new UserClass("Alice", "password123","Alice", 13, 189, 180));
        users.add(new UserClass("Bob", "qwerty", "Builder", 89, 200, 89));
        users.add(new UserClass("Charlie", "letmein","Chaplin", 69, 120, 60));
        users.add(new UserClass("Dave", "securepass","Dave", 45, 210, 89));
        users.add(new UserClass("Eve", "123456", "Adam", 80, 190, 80));

        return users;
    }*/
     private static List<UserClass> readUsersFromFile(String filePath) {
        List<UserClass> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int maxID = 1233;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 10) {  // Validate minimum required fields
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }
                // Parse user data
                int UserID = Integer.parseInt(parts[0]);
                String Username = parts[1];
                String Password = parts[2];
                String name = parts[3];
                int age = Integer.parseInt(parts[4]);
                double height = Double.parseDouble(parts[5]);
                double weight = Double.parseDouble(parts[6]);
                double targetWeight1 = Double.parseDouble(parts[7]);
                double targetWeight2 = Double.parseDouble(parts[8]);
                double targetDistance = Double.parseDouble(parts[9]);
                WeightLoss weightLoss = new WeightLoss(weight, targetWeight1);
                WeightGain weightGain = new WeightGain(weight, targetWeight2);
                Running running = new Running(targetDistance);

                UserClass user = new UserClass(UserID ,Username, Password, name, age, height, weight, weightLoss, weightGain, running);

                // weightloss goals reading
                
                

                users.add(user);
                maxID = Math.max(maxID, UserID);
            }
            UserClass.setIdCounter(maxID+1);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return users;
    }
    public static void addUser(List<UserClass> users, UserClass newUser, String filePath) {
        // Step 1: Add the user to the list
        users.add(newUser);

        // Step 2: Write the user to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String userLine = formatUserForFile(newUser);
            writer.newLine();
            writer.write(userLine);
            System.out.println("User added successfully to the list and file: " + newUser.getUsername());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Converts a UserClass object to a CSV string for file storage
    private static String formatUserForFile(UserClass user) {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getUserID()).append(",");
        sb.append(user.getUsername()).append(",");
        sb.append(user.getPassword()).append(",");
        sb.append(user.getName()).append(",");
        sb.append(user.getAge()).append(",");
        sb.append(user.getHeight()).append(",");
        sb.append(user.getWeight()).append(",");
        sb.append(user.getWeightLoss().getTargetWeight()).append(",");
        sb.append(user.getWeightGain().getTargetWeight()).append(",");
        sb.append(user.getRunning().getDistance());
        return sb.toString();
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


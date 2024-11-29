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
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FitnessTracker {
    private static JFrame frame;
    private static List<UserClass> users;
    public static void HomeScreen(List<UserClass> users){
        frame = new JFrame("Fitness Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Fitness Tracker");
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");

        loginButton.addActionListener(e -> Login());
        signupButton.addActionListener(e -> SignUp(users));

        frame.add(welcomeLabel);
        frame.add(loginButton);
        frame.add(signupButton);

        frame.setVisible(true);
    }
    public static void Login(){
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setSize(319, 287);
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            UserClass loggedInUser = LoginSystem.login(users, username, password);
            if (loggedInUser != null) {
                MainHub(loggedInUser);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.");
            }
        });

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);

        frame.revalidate();
       

            
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
    public static void MainHub(UserClass user){
       System.out.println("Welcome "+user.getName());
    }
    public static void main(String[] args) {
        users = readUsersFromFile("UserInfo.txt");
        HomeScreen(users);
    }

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


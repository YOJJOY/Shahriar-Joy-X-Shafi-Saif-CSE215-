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

    private static List<UserClass> users;
    public static void HomeScreen(){
        JFrame frame = new JFrame("Fitness Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());
        
        frame.setLocationRelativeTo(null);
        
        JLabel welcomeLabel = new JLabel("Welcome to Fitness Tracker");
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");
        
        loginButton.addActionListener(e -> {
            Login();
            frame.dispose();
                });
        signupButton.addActionListener(e -> {
            frame.dispose();
            new SignUpGUI(users);});

        frame.add(welcomeLabel);
        frame.add(loginButton);
        frame.add(signupButton);

        frame.setVisible(true);
    }
    public static void Login(){

        JFrame loginFrame = new JFrame("Fitness Tracker - Login");
        loginFrame.setSize(319, 287);
        loginFrame.setLayout(new FlowLayout());
        loginFrame.setLocationRelativeTo(null);
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        List<UserClass> updatedUsers = readUsersFromFile("UserInfo.txt");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            UserClass loggedInUser = LoginSystem.login(updatedUsers, username, password);
            if (loggedInUser != null) {
                loginFrame.dispose();
                MainHub(loggedInUser);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Please try again.");
            }
        });

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        
        loginFrame.setVisible(true);

            
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
     

    }
    public static void MainHub(UserClass user){
        
        JFrame MainHubframe = new JFrame("Fitness Tracker - Main Hub");
        MainHubframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainHubframe.setSize(400, 300);
        


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        JLabel welcomeLabel = new JLabel("Welcome to Fitness Tracker", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcomeLabel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));


        JButton weightGainButton = new JButton("Weight Gain");
        JButton weightLossButton = new JButton("Weight Loss");
        JButton runningButton = new JButton("Running");


        weightGainButton.addActionListener(e -> WeightGainScreen(user));
        weightLossButton.addActionListener(e -> WeightLossScreen(user));
        runningButton.addActionListener(e -> RunningScreen(user));


        buttonPanel.add(weightGainButton);
        buttonPanel.add(weightLossButton);
        buttonPanel.add(runningButton);


        panel.add(buttonPanel, BorderLayout.CENTER);


        MainHubframe.add(panel);
        MainHubframe.setVisible(true);
    }
    public static void main(String[] args) {
        users = readUsersFromFile("UserInfo.txt");
        HomeScreen();
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

                UserClass user = new UserClass(Username, Password, name, age, height, weight);
                

                users.add(user);
                maxID = Math.max(maxID, UserID);
               
            }
            UserClass.setIdCounter(maxID+1);
            /*for(UserClass user : users){
                System.out.println(" "+user.getUserID()+" "+user.getName());
            }*/
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return users;
    }
     private static void writeUsersToFile(List<UserClass> users, String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        for (UserClass user : users) {
            // Format user data into a CSV string
            String userLine = formatUserForFile(user);
            // Write the formatted line to the file
            writer.write(userLine);
            writer.newLine(); // Move to the next line for the next user
        }
        System.out.println("Users successfully written to file.");
    } catch (IOException e) {
        System.out.println("Error writing to file: " + e.getMessage());
    }
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

    private static void WeightGainScreen(UserClass user) {
    // Create frame
    JFrame weightGainFrame = new JFrame("Fitness Tracker - Weight Gain");
    weightGainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    weightGainFrame.setSize(400, 300);
    weightGainFrame.setLocationRelativeTo(null);

    // Create panel and layout
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 1, 10, 10)); // Three buttons in a column

    // Create buttons
    JButton setGoalButton = new JButton("Set Weight Gain Goal");
    JButton logWeightButton = new JButton("Log Daily Weight");
    JButton viewProgressButton = new JButton("View Weight Gain Progress");
    
    
    
    panel.add(setGoalButton);
    panel.add(logWeightButton);
    panel.add(viewProgressButton);

    // Add panel to the frame
    weightGainFrame.add(panel);

    // Display the frame
    weightGainFrame.setVisible(true);
    }

    private static void WeightLossScreen(UserClass user) {
    JFrame weightLossFrame = new JFrame("Fitness Tracker - Weight Gain");
    weightLossFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    weightLossFrame.setSize(400, 300);
    weightLossFrame.setLocationRelativeTo(null);

    // Create panel and layout
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 1, 10, 10)); // Three buttons in a column

    // Create buttons
    JButton setGoalButton = new JButton("Set Weight Gain Goal");
    JButton logWeightButton = new JButton("Log Daily Weight");
    JButton viewProgressButton = new JButton("View Weight Gain Progress");
    
    
    setGoalButton.addActionListener(e -> addWeightLossGoal(user));
    panel.add(setGoalButton);
    panel.add(logWeightButton);
    panel.add(viewProgressButton);

    // Add panel to the frame
    weightLossFrame.add(panel);

    // Display the frame
    weightLossFrame.setVisible(true);
    }

    private static void RunningScreen(UserClass user) {
        
    }
    private static void addWeightLossGoal(UserClass user){
    JFrame sliderFrame = new JFrame("Set Weight Loss Goal");
    sliderFrame.setSize(400, 200);
    sliderFrame.setLocationRelativeTo(null);
    sliderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Create a new panel for the slider and label
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // Create a slider with a range from 30 to 200 kg, default value at 70
    JSlider weightLossGoalSlider = new JSlider(JSlider.HORIZONTAL, 30, 200, 70);
    weightLossGoalSlider.setMajorTickSpacing(10);
    weightLossGoalSlider.setMinorTickSpacing(1);
    weightLossGoalSlider.setPaintTicks(true);
    weightLossGoalSlider.setPaintLabels(true);

    // Add a label to show the current value of the slider
    JLabel currentGoalLabel = new JLabel("Set your weight loss goal (kg): 70");
    panel.add(currentGoalLabel);
    panel.add(weightLossGoalSlider);

    // Add an event listener to update the label when the slider value changes
    weightLossGoalSlider.addChangeListener(e -> {
        double currentValue = weightLossGoalSlider.getValue();
        currentGoalLabel.setText("Set your weight loss goal (kg): " + currentValue);
    });

    // Create a button to confirm the selected goal
    JButton confirmButton = new JButton("Set Goal");
    confirmButton.addActionListener(e -> {
        double targetWeight = weightLossGoalSlider.getValue();
        user.getWeightLoss().setTargetWeight(targetWeight);
        writeUsersToFile(users, "UserInfo.txt");
        JOptionPane.showMessageDialog(sliderFrame, 
                "Weight loss goal set to: " + targetWeight + " kg", 
                "Goal Set", JOptionPane.INFORMATION_MESSAGE);
        sliderFrame.dispose();  // Close the slider frame
    });

    // Add the confirm button to the panel
    panel.add(confirmButton);

    // Show the frame with the slider
    sliderFrame.add(panel);
    sliderFrame.setVisible(true);

        
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


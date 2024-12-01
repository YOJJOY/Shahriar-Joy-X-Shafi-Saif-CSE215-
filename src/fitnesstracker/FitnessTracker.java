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
import javax.security.auth.callback.Callback;

public class FitnessTracker {

    private static List<UserClass> users;
    private static RunningUpdateCallback callback;
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
        users = readUsersFromFile("UserInfo.txt");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            UserClass loggedInUser = LoginSystem.login(users, username, password);
            if (loggedInUser != null) {
                loginFrame.dispose();
                for (UserClass Listuser : users) {
            System.out.println(Listuser.getUserID());
        if(Listuser.getUserID() == loggedInUser.getUserID()){
            System.out.println("WeightLoss Target: "+loggedInUser.getWeightLoss().getTargetWeight());
        }
                }    
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
       UserClass user = new UserClass(UserName, Password, name, age, height, weight, weight);
       addUser(users, user, "UserInfo.txt");
     

    }
    public static void MainHub(UserClass user){
        
        JFrame MainHubframe = new JFrame("Fitness Tracker - Main Hub");
        MainHubframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainHubframe.setSize(400, 300);
        MainHubframe.setLocationRelativeTo(null);


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


        weightGainButton.addActionListener(e -> {
            WeightGainScreen(user);
            MainHubframe.dispose();
                
                });
        weightLossButton.addActionListener(e -> {
            WeightLossScreen(user);
            MainHubframe.dispose();
        });
        runningButton.addActionListener(e -> {
            RunningScreen(user);
            MainHubframe.dispose();
                });


        buttonPanel.add(weightGainButton);
        buttonPanel.add(weightLossButton);
        buttonPanel.add(runningButton);


        panel.add(buttonPanel, BorderLayout.CENTER);


        MainHubframe.add(panel);
        MainHubframe.setVisible(true);
    }
    public static void main(String[] args) {

        HomeScreen();
    }

     private static List<UserClass> readUsersFromFile(String filePath) {
        List<UserClass> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int maxID = 1233;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 11) {  // Validate minimum required fields
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }
                // Parse user data
                
                if(Double.parseDouble(parts[8])==Double.parseDouble(parts[7]) && Double.parseDouble(parts[9])==0.0 && Double.parseDouble(parts[10])==0.0){
                //System.out.println("UserID: "+ parts[0]+"\n"+"Weight Loss Target: "+Double.parseDouble(parts[7]));
                int UserID = Integer.parseInt(parts[0]);
                String Username = parts[1];
                String Password = parts[2];
                String name = parts[3];
                int age = Integer.parseInt(parts[4]);
                double height = Double.parseDouble(parts[5]);
                double weight = Double.parseDouble(parts[6]);
                double currentweight = Double.parseDouble(parts[7]);
                double targetWeight1 = Double.parseDouble(parts[8]);
                double targetWeight2 = Double.parseDouble(parts[9]);
                double targetDistance = Double.parseDouble(parts[10]);
                WeightLoss weightLoss = new WeightLoss(currentweight, targetWeight1);
                WeightGain weightGain = new WeightGain(currentweight, targetWeight2);
                Running running = new Running(targetDistance, callback);

                UserClass user = new UserClass(Username, Password, name, age, height, weight, currentweight);
                //System.out.println("UserID: "+user.getUserID());

                users.add(user);
                maxID = Math.max(maxID, UserID);
                }
                else if(Double.parseDouble(parts[8])!=Double.parseDouble(parts[7])|| Double.parseDouble(parts[9])!=0.0 || Double.parseDouble(parts[10])!=0.0){
                //System.out.println("UserID: "+ parts[0]+"\n"+"Weight Loss Target: "+Double.parseDouble(parts[7]));
                int UserID = Integer.parseInt(parts[0]);
                String Username = parts[1];
                String Password = parts[2];
                String name = parts[3];
                int age = Integer.parseInt(parts[4]);
                double height = Double.parseDouble(parts[5]);
                double weight = Double.parseDouble(parts[6]);
                double currentweight = Double.parseDouble(parts[7]);
                double targetWeight1 = Double.parseDouble(parts[8]);
                double targetWeight2 = Double.parseDouble(parts[9]);
                double targetDistance = Double.parseDouble(parts[10]);
                WeightLoss weightLoss = new WeightLoss(currentweight, targetWeight1);
                WeightGain weightGain = new WeightGain(currentweight, targetWeight2);
                Running running = new Running(targetDistance, callback);

                UserClass user = new UserClass(Username, Password, name, age, height, weight,currentweight, weightLoss, weightGain, running);
                //System.out.println("UserID: "+user.getUserID());

                users.add(user);
                maxID = Math.max(maxID, UserID);
                }
               
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
        sb.append(user.getCurrentWeight()).append(",");
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
    JButton backButton = new JButton("Back");
    
    logWeightButton.addActionListener(e->{
        updateCurrentWeight(user);
    });
    backButton.addActionListener(e->{
        MainHub(user);
        weightGainFrame.dispose();
    });
    setGoalButton.addActionListener(e->addWeightGainGoal(user));
    viewProgressButton.addActionListener(e->viewWeightGainProgress(user));
    panel.add(setGoalButton);
    panel.add(logWeightButton);
    panel.add(viewProgressButton);
    panel.add(backButton);

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
    JButton setGoalButton = new JButton("Set Weight loss Goal");
    JButton logWeightButton = new JButton("Log Daily Weight");
    JButton viewProgressButton = new JButton("View Weight loss Progress");
    JButton backButton = new JButton("Back");
    
    setGoalButton.addActionListener(e -> addWeightLossGoal(user));
    logWeightButton.addActionListener(e->updateCurrentWeight(user));
    viewProgressButton.addActionListener(e->viewWeightLossProgress(user));
    backButton.addActionListener(e->{
        MainHub(user);
        weightLossFrame.dispose();
    });
    panel.add(setGoalButton);
    panel.add(logWeightButton);
    panel.add(viewProgressButton);
    panel.add(backButton);

    // Add panel to the frame
    weightLossFrame.add(panel);

    // Display the frame
    weightLossFrame.setVisible(true);
    }

    private static void RunningScreen(UserClass user) {
    // Create the frame
    JFrame runningFrame = new JFrame("Fitness Tracker - Running");
    runningFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    runningFrame.setSize(400, 300);
    runningFrame.setLocationRelativeTo(null);

    // Create a panel with GridLayout for buttons
    JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

    // Create the buttons
    JButton setDistanceButton = new JButton("Set Distance");
    JButton startRunButton = new JButton("Start Run");
    JButton backButton = new JButton("Back");

    // Add action listeners
    setDistanceButton.addActionListener(e -> setRunningDistance(user));
    startRunButton.addActionListener(e -> startRun(user));
    backButton.addActionListener(e -> {
        MainHub(user);
        runningFrame.dispose();
    });

    // Add buttons to the panel
    panel.add(setDistanceButton);
    panel.add(startRunButton);
    panel.add(backButton);

    // Add panel to the frame
    runningFrame.add(panel);

    // Display the frame
    runningFrame.setVisible(true);
}
private static void startRun(UserClass user) {
    JFrame startRunFrame = new JFrame("Running - Live");
    startRunFrame.setSize(400, 300);
    startRunFrame.setLocationRelativeTo(null);
    startRunFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JLabel timeLabel = new JLabel("Elapsed Time: 00h 00m 00s", JLabel.CENTER);
    JLabel distanceLabel = new JLabel("Distance Covered: 0.0 km", JLabel.CENTER);
    JButton stopButton = new JButton("Stop Run");

    JPanel panel = new JPanel(new GridLayout(3, 1));
    panel.add(timeLabel);
    panel.add(distanceLabel);
    panel.add(stopButton);

    startRunFrame.add(panel);
    startRunFrame.setVisible(true);

    Running running = new Running(user.getRunning().getDistance(), new RunningUpdateCallback() {
        @Override
        public void onUpdateElapsedTime(String elapsedTime) {
            SwingUtilities.invokeLater(() -> timeLabel.setText("Elapsed Time: " + elapsedTime));
        }

        @Override
        public void onUpdateDistance(double distanceCovered) {
            SwingUtilities.invokeLater(() -> distanceLabel.setText("Distance Covered: " + String.format("%.2f", distanceCovered) + " km"));
        }

        @Override
        public void onWorkoutEnded(double totalDistance) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(startRunFrame,
                        "Workout complete! Total Distance: " + String.format("%.2f", totalDistance) + " km",
                        "Workout Ended", JOptionPane.INFORMATION_MESSAGE);
                startRunFrame.dispose();
            });
        }
    });

    running.startWorkout();

    stopButton.addActionListener(e -> running.endWorkout());
}
private static void setRunningDistance(UserClass user) {
    JFrame distanceFrame = new JFrame("Set Running Distance");
    distanceFrame.setSize(400, 200);
    distanceFrame.setLocationRelativeTo(null);
    distanceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel panel = new JPanel(new FlowLayout());

    JLabel distanceLabel = new JLabel("Set your target distance (km):");
    JTextField distanceField = new JTextField(10);
    JButton setButton = new JButton("Set");

    setButton.addActionListener(e -> {
        try {
            double distance = Double.parseDouble(distanceField.getText());
            user.getRunning().setDistance(distance);
            updateUserInfo(users, user);
            writeUsersToFile(users, "UserInfo.txt");
            JOptionPane.showMessageDialog(distanceFrame, 
                    "Running distance set to: " + distance + " km", 
                    "Distance Set", JOptionPane.INFORMATION_MESSAGE);
            distanceFrame.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(distanceFrame, 
                    "Please enter a valid number.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    panel.add(distanceLabel);
    panel.add(distanceField);
    panel.add(setButton);

    distanceFrame.add(panel);
    distanceFrame.setVisible(true);
}
    private static void viewWeightLossProgress(UserClass user){

    double weightDifference = user.getWeightLoss().WeightDifference();
    double currentWeight = user.getWeightLoss().getCurrentWeight();
    double targetWeight = user.getWeightLoss().getTargetWeight();
    double startingweight = user.getWeight();
        System.out.println(startingweight);


    JFrame progressFrame = new JFrame("Weight Loss Progress");
    progressFrame.setSize(400, 250);
    progressFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    progressFrame.setLocationRelativeTo(null);


    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


    JLabel progressLabel = new JLabel("Current Weight: " + currentWeight + " kg, Target Weight: " + targetWeight + " kg");
    progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(progressLabel);


    int progressPercentage = 0;
    double totalloss = startingweight - targetWeight;
        System.out.println("Total Loss: "+totalloss);
    if(currentWeight <= targetWeight){
        progressPercentage = 100;
    }
    if (currentWeight > targetWeight) {
        
        progressPercentage = (int) (((totalloss-(currentWeight-targetWeight))/totalloss)*100);
        System.out.println("progress percentage: "+progressPercentage);
    }


    JProgressBar progressBar = new JProgressBar(0, 100);
    progressBar.setValue(progressPercentage);
    progressBar.setStringPainted(true);
    progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
    panel.add(progressBar);


    JLabel remainingLabel = new JLabel("Weight left to lose: " + weightDifference + " kg");
    remainingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
    panel.add(remainingLabel);


    JButton closeButton = new JButton("Close");
    closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    closeButton.addActionListener(e -> progressFrame.dispose());
    panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
    panel.add(closeButton);


    progressFrame.add(panel);


    progressFrame.setVisible(true);
    }
    private static void updateCurrentWeight(UserClass user) {
    // Create frame for updating weight
    JFrame updateWeightFrame = new JFrame("Update Current Weight");
    updateWeightFrame.setSize(400, 200);
    updateWeightFrame.setLocationRelativeTo(null);
    updateWeightFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Create a panel with a BorderLayout
    JPanel panel = new JPanel(new BorderLayout());

    // Create a smaller panel for input (centered)
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

    // Create a label and text field for entering the new weight
    JLabel currentWeightLabel = new JLabel("Enter your current weight (kg):");
    JTextField weightField = new JTextField(10); // Smaller text field width

    // Add label and text field to the input panel
    inputPanel.add(currentWeightLabel);
    inputPanel.add(weightField);

    // Create a button for updating weight
    JButton updateButton = new JButton("Update Weight");

    // Place the input panel in the center and the button in the south
    panel.add(inputPanel, BorderLayout.CENTER);
    panel.add(updateButton, BorderLayout.SOUTH);

    // Add an action listener to the button
    updateButton.addActionListener(e -> {
        try {
            double newWeight = Double.parseDouble(weightField.getText());

            // Update the user's weight in memory
            user.setCurrentWeight(newWeight);
            updateUserInfo(users, user);

            // Save the updated weight to the file
            writeUsersToFile(users, "UserInfo.txt");

            // Show success message
            JOptionPane.showMessageDialog(updateWeightFrame, 
                    "Weight updated successfully to: " + newWeight + " kg", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Close the frame
            updateWeightFrame.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(updateWeightFrame, 
                    "Please enter a valid number for the weight.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Add the panel to the frame
    updateWeightFrame.add(panel);

    // Display the frame
    updateWeightFrame.setVisible(true);
}
    private static void addWeightGainGoal(UserClass user){
    JFrame sliderFrame = new JFrame("Set Weight Gain Goal");
    sliderFrame.setSize(400, 200);
    sliderFrame.setLocationRelativeTo(null);
    sliderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JSlider weightGainGoalSlider = new JSlider(JSlider.HORIZONTAL, (int) user.getCurrentWeight(), 200, (int) user.getCurrentWeight() + 5);
    weightGainGoalSlider.setMajorTickSpacing(5);
    weightGainGoalSlider.setMinorTickSpacing(1);
    weightGainGoalSlider.setPaintTicks(true);
    weightGainGoalSlider.setPaintLabels(true);

    JLabel currentGoalLabel = new JLabel("Set your weight gain goal (kg): " + user.getCurrentWeight());
    panel.add(currentGoalLabel);
    panel.add(weightGainGoalSlider);

    weightGainGoalSlider.addChangeListener(e -> {
        double currentValue = weightGainGoalSlider.getValue();
        currentGoalLabel.setText("Set your weight gain goal (kg): " + currentValue);
    });

    JButton confirmButton = new JButton("Set Goal");
    confirmButton.addActionListener(e -> {
        double targetWeight = weightGainGoalSlider.getValue();
        user.getWeightGain().setTargetWeight(targetWeight);
        updateUserInfo(users, user);
        writeUsersToFile(users, "UserInfo.txt");
        JOptionPane.showMessageDialog(sliderFrame, 
                "Weight gain goal set to: " + targetWeight + " kg", 
                "Goal Set", JOptionPane.INFORMATION_MESSAGE);
        sliderFrame.dispose();
    });

    panel.add(confirmButton);
    sliderFrame.add(panel);
    sliderFrame.setVisible(true);
    }
    private static void viewWeightGainProgress(UserClass user){
    double weightDifference = user.getWeightGain().WeightDifference();
    double currentWeight = user.getWeightGain().getCurrentWeight();
    double targetWeight = user.getWeightGain().getTargetWeight();
    double startingWeight = user.getWeight();
        System.out.println("target weight: "+targetWeight);
        System.out.println("starting weight: "+startingWeight);
    JFrame progressFrame = new JFrame("Weight Gain Progress");
    progressFrame.setSize(400, 250);
    progressFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    progressFrame.setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JLabel progressLabel = new JLabel("Current Weight: " + currentWeight + " kg, Target Weight: " + targetWeight + " kg");
    progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(progressLabel);

    int progressPercentage = 0;
 

    if (currentWeight >= targetWeight) {
        progressPercentage = 100;
    } else {
        progressPercentage = (int) ((((currentWeight))/targetWeight)*100);
        System.out.println("progress percentage: "+progressPercentage);
    }

    JProgressBar progressBar = new JProgressBar(0, 100);
    progressBar.setValue(progressPercentage);
    progressBar.setStringPainted(true);
    progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));
    panel.add(progressBar);

    JLabel remainingLabel = new JLabel("Weight left to gain: " + weightDifference + " kg");
    remainingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(remainingLabel);

    JButton closeButton = new JButton("Close");
    closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    closeButton.addActionListener(e -> progressFrame.dispose());
    panel.add(Box.createRigidArea(new Dimension(0, 20)));
    panel.add(closeButton);

    progressFrame.add(panel);
    progressFrame.setVisible(true);
    }
    private static void addWeightLossGoal(UserClass user){
    JFrame sliderFrame = new JFrame("Set Weight Loss Goal");
    sliderFrame.setSize(400, 200);
    sliderFrame.setLocationRelativeTo(null);
    sliderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

   
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

   
    JSlider weightLossGoalSlider = new JSlider(JSlider.HORIZONTAL, 30, 200, 70);
    weightLossGoalSlider.setMajorTickSpacing(10);
    weightLossGoalSlider.setMinorTickSpacing(1);
    weightLossGoalSlider.setPaintTicks(true);
    weightLossGoalSlider.setPaintLabels(true);

   
    JLabel currentGoalLabel = new JLabel("Set your weight loss goal (kg): 70");
    panel.add(currentGoalLabel);
    panel.add(weightLossGoalSlider);

   
    weightLossGoalSlider.addChangeListener(e -> {
        double currentValue = weightLossGoalSlider.getValue();
        currentGoalLabel.setText("Set your weight loss goal (kg): " + currentValue);
    });

   
    JButton confirmButton = new JButton("Set Goal");
    confirmButton.addActionListener(e -> {
        double targetWeight = weightLossGoalSlider.getValue();
        user.getWeightLoss().setTargetWeight(targetWeight);
        updateUserInfo(users, user);
        writeUsersToFile(users, "UserInfo.txt");
        JOptionPane.showMessageDialog(sliderFrame, 
                "Weight loss goal set to: " + targetWeight + " kg", 
                "Goal Set", JOptionPane.INFORMATION_MESSAGE);
        sliderFrame.dispose();  
    });

    
    panel.add(confirmButton);

   
    sliderFrame.add(panel);
    sliderFrame.setVisible(true);

        
    }
    public static void updateUserInfo(List<UserClass> users, UserClass user) {
        System.out.println(user.getUserID());
        for (UserClass Listuser : users) {
            System.out.println(Listuser.getUserID());
        if(Listuser.getUserID() == user.getUserID()){
            //System.out.println("weightloss target "+Listuser.getWeightLoss().getTargetWeight());
            Listuser.getWeightLoss().setTargetWeight(user.getWeightLoss().getTargetWeight());
            Listuser.getWeightGain().setTargetWeight(user.getWeightGain().getTargetWeight());
            Listuser.getWeightLoss().setCurrentWeight(user.getCurrentWeight());
            Listuser.getWeightGain().setCurrentWeight(user.getCurrentWeight());
            Listuser.setWeight(user.getWeight());
            Listuser.setCurrentWeight(user.getCurrentWeight());
            Listuser.getRunning().setDistance(user.getRunning().getDistance());
            //System.out.println("weightloss target "+Listuser.getWeightLoss().getTargetWeight());
            break;
        }
    }
}
    
}
class LoginSystem {
    public static UserClass login(List<UserClass> users, String inputUsername, String inputPassword) {
        // Check if any user matches the provided credentials
        for (UserClass user : users) {
            if (user.getUsername().equals(inputUsername) && user.getPassword().equals(inputPassword)) {
                System.out.println("Login successful! Welcome, " + user.getUsername() + " "+"ID: "+user.getUserID());
                return user;
            }
        }

        System.out.println("Invalid username or password.");
        return null;
    }
    
    
}


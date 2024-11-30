/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fitnesstracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
public class SignUpGUI {
     private JFrame frame;
    private JTextField usernameField, passwordField, nameField, ageField, heightField, weightField;
    private JTextArea userInfoDisplay;

    public SignUpGUI(List<UserClass> users) {
        
        frame = new JFrame("User Sign-Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        
        JLabel usernameLabel = new JLabel("Enter Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordField = new JTextField();
        JLabel nameLabel = new JLabel("Enter Name:");
        nameField = new JTextField();
        JLabel ageLabel = new JLabel("Enter Age:");
        ageField = new JTextField();
        JLabel heightLabel = new JLabel("Enter Height:");
        heightField = new JTextField();
        JLabel weightLabel = new JLabel("Enter Weight:");
        weightField = new JTextField();

        JButton submitButton = new JButton("Sign Up");
        JButton backButton = new JButton("Homescreen");
        
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(new JLabel());
        panel.add(submitButton);
        panel.add(new JLabel());
        panel.add(backButton);

        
        userInfoDisplay = new JTextArea(10, 30);
        userInfoDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(userInfoDisplay);

        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp(users);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FitnessTracker.HomeScreen();
                frame.dispose();
            }
        });

        
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void handleSignUp(List<UserClass> users) {
        try {
            
            String username = usernameField.getText();
            String password = passwordField.getText();
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            
            UserClass newUser = new UserClass(username, password, name, age, height, weight);

            
            FitnessTracker.addUser(users, newUser, "UserInfo.txt");

            // Display User Info
            userInfoDisplay.setText(""); 
            for (UserClass user : users) {
                userInfoDisplay.append("Username: " + user.getUsername() + "\n");
                userInfoDisplay.append("Password: " + user.getPassword() + "\n");
                userInfoDisplay.append("User ID: " + user.getUserID() + "\n");
                userInfoDisplay.append("Name: " + user.getName() + "\n");
                userInfoDisplay.append("Age: " + user.getAge() + "\n");
                userInfoDisplay.append("Height: " + user.getHeight() + "\n");
                userInfoDisplay.append("Weight: " + user.getWeight() + "\n");
                userInfoDisplay.append("Weight Loss Target: " + user.getWeightLossTarget() + "\n");
                userInfoDisplay.append("Weight Gain Target: " + user.getWeightGainTarget() + "\n");
                userInfoDisplay.append("Running Distance: " + user.getRunningTaget() + "\n");
                userInfoDisplay.append("----------------------------------\n");
            }

            JOptionPane.showMessageDialog(frame, "Sign-Up Successful!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numeric values for Age, Height, and Weight.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
}
}

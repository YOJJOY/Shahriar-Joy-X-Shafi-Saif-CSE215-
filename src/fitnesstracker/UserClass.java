/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fitnesstracker;

/**
 *
 * @author SHAFI
 */
public class UserClass {
     private static int idCounter = 1234;
    private int UserID;
    private String Username;
    private String Password;
    private String name;
    private int age;
    private double height;
    private double weight;
    private WeightLoss weightLoss;
    private WeightGain weightGain;
    private Running running;
    
    UserClass(){
        UserID++;
    }

    public UserClass(String username, String password, String name, int age, double height, double weight,
                     WeightLoss weightLoss, WeightGain weightGain, Running running) {
        this.UserID = idCounter++; // Auto-increment userID
        this.Username = username;
        this.Password = password;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.weightLoss = weightLoss;
        this.weightGain = weightGain;
        this.running = running;
    }

    public UserClass(String Username, String Password, String name, int age, double height, double weight) {
        this(Username, Password, name, age, height, weight, new WeightLoss(weight, 0.0), new WeightGain(weight, 0.0), new Running(0.0));
    }

    public UserClass(int UserID,String Username, String Password, String name, int age, double height, double weight, WeightLoss weightLoss, WeightGain weightGain, Running running) {
        this.Username = Username;
        this.Password = Password;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.weightLoss = weightLoss;
        this.weightGain = weightGain;
        this.running = running;
    }
    
    
    

    public int getUserID() {
        return UserID;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public WeightLoss getWeightLoss() {
        return weightLoss;
    }

    public WeightGain getWeightGain() {
        return weightGain;
    }

    public Running getRunning() {
        return running;
    }

    public void setWeightLoss(WeightLoss weightLoss) {
        this.weightLoss = weightLoss;
    }

    public void setWeightGain(WeightGain weightGain) {
        this.weightGain = weightGain;
    }

    public void setRunning(Running running) {
        this.running = running;
    }
    public double getWeightLossTarget(){
        return weightLoss.getTargetWeight();
    }
    public double getWeightGainTarget(){
        return weightGain.getTargetWeight();
    }
    public double getRunningTaget(){
        return running.getDistance();
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        UserClass.idCounter = idCounter;
    }

    @Override
    public String toString() {
        return "UserClass{" + "UserID=" + UserID + ", Username=" + Username + ", Password=" + Password + ", name=" + name + ", age=" + age + ", height=" + height + ", weight=" + weight + ", weightLoss=" + weightLoss + ", weightGain=" + weightGain + ", running=" + running + '}';
    }

  


    
    
}

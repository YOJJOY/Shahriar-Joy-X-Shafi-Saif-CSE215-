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
    private int UserID = 0;
    private String Username;
    private String Password;
    private String name;
    private int age;
    private double height;
    private double weight;
    
    UserClass(){
        UserID++;
    }

    public UserClass(String Username, String Password, String name, int age, double height, double weight) {
        this.Username = Username;
        this.Password = Password;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
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

    @Override
    public String toString() {
        return "UserClass{" + "name=" + name + ", age=" + age + ", height=" + height + ", weight=" + weight + '}';
    }
    
}

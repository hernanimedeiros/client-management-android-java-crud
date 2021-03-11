//Declaration of package
package com.iefp.loginsqlitecrud.Models;

public class Student{

    //Private attributes.
    private int number;
    private String name;
    private int age;
    private String phone;
    private String email;

    //Constructor.
    public Student(int number, String name, int age, String phone, String email){
        this.number = number;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
    }

    //Getters and Setters.
    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

}

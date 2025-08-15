package com.example.cse213_finalproject_group65_flighttrainingacademy;
//Created By Md. Saifur Rahman
import java.time.LocalDate;

public class User {
    private String id, name, phoneNo, email, address, gender, password;
    private LocalDate dob;

    public User(String id, String name, String phoneNo, String email, String address, String gender, String password, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.password = password;
        this.dob = dob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\n' +
                "name='" + name + '\n' +
                "phoneNo='" + phoneNo + '\n' +
                "email='" + email + '\n' +
                "address='" + address + '\n' +
                "gender='" + gender + '\n' +
                "password='" + password + '\n' +
                "dob=" + dob +
                '}';
    }
}

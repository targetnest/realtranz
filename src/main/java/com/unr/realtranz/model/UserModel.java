package com.unr.realtranz.model;

import javax.persistence.Column;
import javax.persistence.Id;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:07-06-2022 01:29
 **/
public class UserModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String email_id;
    private String username;
    private String phone1;
    private String phone2;
    private String occupation;
    private Long income;

    private String nationality;
    private String state;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private Integer pinNumber;

    public UserModel(Long id,String firstName, String lastName, String email_id, String username, String phone1, String phone2, String occupation, Long income,
                     String nationality,String addressLine1,String addressLine2,String city,String state,Integer pinNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email_id = email_id;
        this.username = username;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.occupation = occupation;
        this.income = income;
        this.nationality = nationality;
        this.addressLine1= addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.pinNumber = pinNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Integer getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(Integer pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }
}

package entity;

import helper.Gender;

public class Person {

    private final Gender gender;
    private String name;
    private int contactNo;

    public Person(String name, int contactNo, Gender gender) {
        this.name = name;
        this.contactNo = contactNo;
        this.gender = gender;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactNo() {
        return this.contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public Gender getGender() {
        return this.gender;
    }
}
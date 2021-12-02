package entity;

import helper.Gender;

public class Customer extends Person {

    private final boolean isMember;

    public Customer(String name, int contactNo, boolean isMember, Gender gender) {
        super(name, contactNo, gender);
        this.isMember = isMember;
    }

    public boolean getMembership() {
        return this.isMember;
    }
}
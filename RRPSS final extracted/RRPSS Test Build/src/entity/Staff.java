package entity;

import helper.Gender;

/**
 * Test
 *
 * @author pekxu
 */
public class Staff extends Person implements Comparable<Staff> {

    private int employeeID;
    private String jobTitle;

    public Staff(String fullName, int contactNo, String jobTitle, int employeeID, Gender gender) {
        super(fullName, contactNo, gender);
        this.employeeID = employeeID;
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getEmployeeID() {
        return this.employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    @Override
    public String toString() {
        // %-10s Left-justifying given String within 10 spaces
        return String.format("#%-7d %-20s %-8d %-10s",
                this.getEmployeeID(), this.getName(), this.getContactNo(), this.getJobTitle().toUpperCase());
    }

    @Override
    public int compareTo(Staff compareStaff) {
        return this.getEmployeeID() - compareStaff.getEmployeeID();
    }

    // There is no other way to do this.
    // staff.equals(staffB) doesn't work
    public boolean isEquals(Staff staff) {
        return (this.getName().equalsIgnoreCase(staff.getName())) &&
                (this.getEmployeeID() == staff.getEmployeeID()) &&
                (this.getJobTitle().equalsIgnoreCase(staff.getJobTitle())) &&
                (this.getContactNo() == staff.getContactNo()) &&
                (this.getGender().equals(staff.getGender()));
    }
}
package control;

import entity.Staff;

import java.util.ArrayList;
import java.util.Collections;

public class StaffManager {

    private final ArrayList<Staff> staffList;

    public StaffManager(ArrayList<Staff> staffList) {
        this.staffList = staffList;
    }

    /**
     * Returns the staff at the specified position in this list.
     *
     * @param index index of the staff to return
     * @return the staff at the specified position in this list
     */
    public Staff getStaff(int index) {
        return staffList.get(index);
    }

    public ArrayList<Staff> getStaffList() {
        return this.staffList;
    }

    /**
     * Updates the name of the {@link Staff} at the specified position in the staffList.
     *
     * @param staffNo   index of staff to be updated
     * @param staffName new name of the element
     */
    public void updateName(int staffNo, String staffName) {
        staffList.get(staffNo).setName(staffName);
    }

    /**
     * Updates the contact no. of the {@link Staff} at the specified position in the staffList.
     *
     * @param staffNo   index of staff to be updated
     * @param contactNo new contact no. of the staff
     */
    public void updateContactNo(int staffNo, int contactNo) {
        staffList.get(staffNo).setContactNo(contactNo);
    }

    /**
     * Updates the job title of the {@link Staff} at the specified position in the staffList.
     *
     * @param staffNo  index of staff to be updated
     * @param jobTitle new job title of the staff
     */
    public void updateJobTitle(int staffNo, String jobTitle) {
        staffList.get(staffNo).setJobTitle(jobTitle);
    }

    /**
     * Updates the employee ID of the {@link Staff} at the specified position in the staffList.
     *
     * @param staffNo    index of staff to be updated
     * @param employeeID new employee ID of the staff
     */
    public void updateEmployeeID(int staffNo, int employeeID) {
        staffList.get(staffNo).setEmployeeID(employeeID);
    }

    public void addStaff(Staff newStaff) {

        for (Staff staff : staffList) {
            if (staff.isEquals(newStaff)) {
                System.out.println("\n* Error! Staff already exists. *");
                return;
            }
        }
        staffList.add(newStaff);
    }

    public void removeStaff(int staffChoice) {
        staffList.remove(staffChoice);
    }

    /**
     * Returns the number of staff in staffList.
     *
     * @return the number of staff in staffList
     */
    public int getStaffListLength() {
        return staffList.size();
    }

    /**
     * Prints details of all {@link Staff} in this list.
     */
    public void printStaffList() {
        int i = 1;
        Collections.sort(staffList);

        System.out.println();
        System.out.printf("---- %-8s %-20s %-8s %-10s\n", "STAFF ID", "FULL NAME", "CONTACT", "JOB TITLE");
        for (Staff staff : staffList) {
            System.out.printf("(%-2d) %s\n", i++, staff);
        }
    }
}
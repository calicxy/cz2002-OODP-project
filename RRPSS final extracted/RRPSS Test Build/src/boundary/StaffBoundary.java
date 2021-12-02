package boundary;

import control.StaffManager;
import entity.Staff;
import helper.Gender;
import helper.ScannerPlus;

import java.util.Scanner;

public class StaffBoundary {

    private final StaffManager staffManager;
    private final ScannerPlus scannerPlus = new ScannerPlus();
    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructor for StaffBoundary.
     *
     * @param staffManager staffManager instance for constructor injection
     */
    public StaffBoundary(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    public void init() {

        int choice;
        do {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("-         STAFF MANAGER        -");
            System.out.println("--------------------------------");
            System.out.println("[1] View All Staff");
            System.out.println("[2] Edit Staff Particulars");
            System.out.println("[3] Add New Staff");
            System.out.println("[4] Remove Staff");
            System.out.println("[5] Exit Staff Manager");
            choice = scannerPlus.nextIntRange("Select choice", 1, 5);

            switch (choice) {
                case 1 -> staffManager.printStaffList();
                case 2 -> updateStaff();
                case 3 -> addStaff();
                case 4 -> removeStaff();
                case 5 -> System.out.println("Returning...");
            }
        } while (choice != 5);

        System.out.println();
    }

    public void updateStaff() {
        staffManager.printStaffList();

        int staffIndex = scannerPlus.nextIntRange("Select staff to edit", 1, staffManager.getStaffListLength()) - 1;

        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("[1] Update Name");
        System.out.println("[2] Update Contact No.");
        System.out.println("[3] Update Job Title");
        System.out.println("[4] Update Employee ID");

        switch (scannerPlus.nextIntRange("Select choice", 1, 5)) {
            case 1 -> {
                System.out.print("Enter new staff name :: ");
                staffManager.updateName(staffIndex, sc.nextLine());
            }
            case 2 -> {
                int contactNo = scannerPlus.nextInt("Enter new contact no.");
                staffManager.updateContactNo(staffIndex, contactNo);
            }
            case 3 -> {
                System.out.print("Enter new job title :: ");
                staffManager.updateJobTitle(staffIndex, sc.nextLine());
            }
            case 4 -> {
                int employeeID = scannerPlus.nextInt("Enter new employee ID");
                staffManager.updateEmployeeID(staffIndex, employeeID);
            }
        }
    }

    public void addStaff() {
        System.out.print("Enter new staff name :: ");
        String staffName = sc.nextLine();

        int contactNo = scannerPlus.nextInt("Enter staff contact no.");

        System.out.print("Enter staff job title :: ");
        String jobTitle = sc.nextLine();

        int choice;
        Gender staffGender = null;
        System.out.println("SELECT GENDER");
        System.out.println("[1] Male");
        System.out.println("[2] Female");
        System.out.println("[3] Others");
        choice = scannerPlus.nextIntRange("Select choice", 1, 3);
        switch (choice) {
            case 1 -> staffGender = Gender.MALE;
            case 2 -> staffGender = Gender.FEMALE;
            case 3 -> staffGender = Gender.OTHERS;
        }

        int employeeID = scannerPlus.nextInt("Enter staff employee ID");
        Staff newStaff = new Staff(staffName, contactNo, jobTitle, employeeID, staffGender);

        staffManager.addStaff(newStaff);
    }

    public void removeStaff() {
        staffManager.printStaffList();
        int staffChoice = scannerPlus.nextIntRange("Select staff to remove (0 to abort)", 0, staffManager.getStaffListLength()) - 1;

        if (staffChoice == -1) {
            System.out.println("Successfully aborted.");
            return;
        }

        staffManager.removeStaff(staffChoice);
    }
}

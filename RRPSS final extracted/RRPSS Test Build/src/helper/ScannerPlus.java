package helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerPlus {

    private final Scanner sc = new Scanner(System.in);

    public int nextInt(String message) {

        int inputInteger = 0;
        boolean inputSuccessful = false;

        do {
            try {
                System.out.print(message + " :: ");
                inputInteger = sc.nextInt();
                inputSuccessful = true;
            } catch (InputMismatchException e) {
                System.out.println("ERROR. Please enter a number.");
                sc.nextLine();
            }
        } while (!inputSuccessful);

        return inputInteger;
    }

    public double nextDouble(String message) {

        double inputDouble = 0;
        boolean inputSuccessful = false;

        do {
            try {
                System.out.print(message + " :: ");
                inputDouble = sc.nextDouble();
                inputSuccessful = true;
            } catch (InputMismatchException e) {
                System.out.println("ERROR. Please enter a number.");
                sc.nextLine();
            }
        } while (!inputSuccessful);

        return inputDouble;
    }

    public int nextIntRange(String message, int lower, int upper) {

        int inputInteger = 0;
        boolean inputSuccessful = false;

        do {
            try {
                System.out.print(message + " :: ");
                inputInteger = sc.nextInt();

                if ((inputInteger >= lower) && (inputInteger <= upper)) {
                    inputSuccessful = true;
                } else {
                    System.out.println("ERROR. Please enter a number within the range.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR. Please enter a number.");
                sc.nextLine();
            }
        } while (!inputSuccessful);

        return inputInteger;
    }

    public boolean nextBoolean(String message) {

        boolean inputBoolean = true;
        boolean inputSuccessful = false;

        do {
            try {
                System.out.print(message + " :: ");
                inputBoolean = sc.nextBoolean();
                inputSuccessful = true;
            } catch (InputMismatchException e) {
                System.out.println("ERROR. Please enter true/false.");
                sc.nextLine();
            }
        } while (!inputSuccessful);

        return inputBoolean;
    }
}

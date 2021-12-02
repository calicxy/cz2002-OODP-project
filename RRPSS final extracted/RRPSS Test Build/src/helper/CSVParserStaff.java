package helper;

import entity.Staff;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

//Original source : https://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html
public class CSVParserStaff {

    public static ArrayList<Staff> readFromCSV(String fileName) {
        ArrayList<Staff> staffList = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with comma as delimiter
                String[] attributes = line.split(",");

                try {
                    Staff staff = createStaff(attributes);
                    staffList.add(staff);
                } catch (Exception e) {
                    // if parsing error - object creation unsuccessful
                    System.out.printf("Error when parsing Staff - %s. Exit IMMEDIATELY and check data files.\n", line.split(",")[0]);
                }

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return staffList;
    }

    private static Staff createStaff(String[] metadata) {

        String name = metadata[0];
        int contactNo = Integer.parseInt(metadata[1]);
        String jobTitle = metadata[2];
        int employeeID = Integer.parseInt(metadata[3]);
        Gender gender = Gender.valueOf(metadata[4]);

        return new Staff(name, contactNo, jobTitle, employeeID, gender);
    }
}
package helper;

import entity.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

// Original source : https://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html
public class CSVParserMenuItem {

    public static ArrayList<MenuItem> readFromCSV(String fileName) {
        ArrayList<MenuItem> menuList = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array using "equals" as the delimiter
                String[] attributes = line.split("=");

                try {
                    MenuItem item = createMenuItem(attributes);
                    menuList.add(item);
                } catch (Exception e) {
                    // if parsing error - object creation unsuccessful
                    System.out.printf("Error when parsing MenuItem - %s. Exit IMMEDIATELY and check data files.\n", line.split("=")[0]);
                }

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return menuList;
    }

    private static MenuItem createMenuItem(String[] metadata) {

        String name = metadata[0];
        double price = Double.parseDouble(metadata[1]);
        String description = metadata[2];
        int categoryNo = Integer.parseInt(metadata[3]);

        return new MenuItem(name, price, description, categoryNo);
    }
}
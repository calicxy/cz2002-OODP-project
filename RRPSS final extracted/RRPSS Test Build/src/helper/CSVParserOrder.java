package helper;

import entity.PastOrderData;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

//Original source : https://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html
public class CSVParserOrder {

    public static ArrayList<PastOrderData> readFromCSV(String fileName) {
        ArrayList<PastOrderData> pastOrderDataList = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with "equals" as delimiter
                String[] attributes = line.split(",");

                try {
                    PastOrderData pastOrderData = createPastOrderData(attributes);
                    pastOrderDataList.add(pastOrderData);
                } catch (Exception e) {
                    // if parsing error - object creation unsuccessful
                    System.out.printf("Error when parsing Order - %s. Please check data files.\n", line.split(",")[0]);
                }

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return pastOrderDataList;
    }

    private static PastOrderData createPastOrderData(String[] metadata) {

        LocalDateTime timeClosed = LocalDateTime.parse(metadata[0]);
        double bill = Double.parseDouble(metadata[1]);
        ArrayList<String> itemLine = new ArrayList<>();

        int i = 2;
        while (!metadata[i].equals("###")) {
            itemLine.add(metadata[i]);
            i++;
        }

        return new PastOrderData(timeClosed, bill, itemLine);
    }
}
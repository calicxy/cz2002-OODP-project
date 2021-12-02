package helper;

import control.TableManager;
import entity.Customer;
import entity.Reservation;
import entity.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class CSVParserReservation {
    // Create arrayList before feeding in the value

    TableManager tableManager;

    public CSVParserReservation(TableManager tableManager) {
        this.tableManager = tableManager;
    }

    public ArrayList<Reservation> readFromCSV(String fileName) {

        ArrayList<Reservation> actRe = new ArrayList<>();

        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                String[] attributes = line.split(",");

                try {
                    Reservation re = createReservation(attributes);

                    // ZW: >= 120
                    // Only add active reservations into the list
                    if (ChronoUnit.MINUTES.between(LocalDateTime.now(), re.getReservationTime()) >= 0) {
                        actRe.add(re);
                    }

                } catch (Exception e) {
                    // if parsing error - object creation unsuccessful
                    System.out.println("Error when parsing reservations.csv. Exit IMMEDIATELY and check data files.");
                }

                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return actRe;
    }

    private Reservation createReservation(String[] metadata) {

        int tableId = Integer.parseInt(metadata[0]);

        // NEED ADD IN TRY CATCH
        String timing = metadata[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime reservedTime = LocalDateTime.parse(timing, formatter);

        String name = metadata[2];
        int contact = Integer.parseInt(metadata[3]);
        boolean isMember = Boolean.parseBoolean(metadata[4]);
        Gender gender = Gender.valueOf(metadata[5]);
        int pax = Integer.parseInt(metadata[6]);
        int capacity = Integer.parseInt(metadata[7]);

        Customer customer = new Customer(name, contact, isMember, gender);
        Table table = tableManager.getTable(tableId, capacity);

        // create and return reservation of this metadata
        return new Reservation(customer, pax, reservedTime, table);
    }
}

// Reference: Read CSV and create object from CSV 
// https://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html
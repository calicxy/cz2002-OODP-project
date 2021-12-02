package helper;

import entity.MenuItem;
import entity.PromoSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

//Original source : https://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html
public class CSVParserPromoSet {

    private final ArrayList<ArrayList<MenuItem>> menu;

    public CSVParserPromoSet(ArrayList<ArrayList<MenuItem>> menu) {
        this.menu = menu;
    }

    public ArrayList<PromoSet> readFromCSV(String fileName) {
        ArrayList<PromoSet> promoSetList = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with "equals" as delimiter
                String[] attributes = line.split("=");

                try {
                    PromoSet item = createPromoSet(attributes);
                    promoSetList.add(item);
                } catch (Exception e) {
                    // if parsing error - object creation unsuccessful
                    System.out.printf("Error when parsing PromoSet - %s. Exit IMMEDIATELY and check data files.\n", line.split("=")[0]);
                }

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return promoSetList;
    }

    private PromoSet createPromoSet(String[] metadata) {

        String name = metadata[0];
        double price = Double.parseDouble(metadata[1]);
        String description = metadata[2];
        int categoryNo = Integer.parseInt(metadata[3]);

        // Create PromoSet without any MenuItems
        PromoSet result = new PromoSet(name, price, description, categoryNo);

        MenuItem itemToAdd;

        int i = 4;
        int menuItemCategory;
        String menuItemName;
        String[] line;

        // End Of Line is demarcated by "###"
        while (!metadata[i].equals("###")) {

            line = metadata[i].split("XXX");
            menuItemCategory = Integer.parseInt(line[0]);
            menuItemName = line[1];

            itemToAdd = findMenuItem(menuItemCategory, menuItemName);

            // Return null if invalid MenuItem entry in .csv file
            if (itemToAdd == null) {
                throw new IndexOutOfBoundsException();
            }
            result.addItem(itemToAdd);

            i++;
        }

        // create and return book of this metadata
        return result;
    }

    public MenuItem findMenuItem(int category, String name) {

        for (MenuItem item : menu.get(category)) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}

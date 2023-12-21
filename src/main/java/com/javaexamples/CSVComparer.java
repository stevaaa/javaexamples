package com.javaexamples;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVComparer {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        // Get the input CSV files
        File csv1 = new File("/Users/stephensam/projects/MP/javaexamples/src/main/resources/input1.csv");
        File csv2 = new File("/Users/stephensam/projects/MP/javaexamples/src/main/resources/input2.csv");

        // Create a CSVParser object for each file
        CSVParser parser1 = new CSVParser(new InputStreamReader(csv1.toURI().toURL().openStream()), CSVFormat.DEFAULT);
        CSVParser parser2 = new CSVParser(new InputStreamReader(csv2.toURI().toURL().openStream()), CSVFormat.DEFAULT);

        // Create a list to store the results
        List<Map<String, Object>> results = new ArrayList<>();

        // Iterate over the rows in the first file
        for (CSVRecord record1 : parser1) {

            // Get the row index
            int rowIndex = (int) record1.getRecordNumber();

            // Check if the row exists in the second file
            boolean found = false;
            for (CSVRecord record2 : parser2) {
                if (record1.equals(record2)) {
                    found = true;
                    break;
                }

            // If the row does not exist in the second file, add it to the results
            if (!found) {
                Map<String, Object> result = new HashMap<>();
                result.put("RowIndex", rowIndex);
                result.put("occurrences in csv1", 1);
                result.put("occurrences in csv2", 0);
                result.put("Identical?", false);
                result.put("NonIdentical columns", new HashMap<>());
                result.put("csv1 record", record1);
                result.put("csv2 record", null);
                results.add(result);
            } else {

                // If the row exists in the second file, check if the values are identical
                boolean identical = true;
                for (int i = 0; i < record1.size(); i++) {
                    if (!record1.get(i).equals(record2.get(i))) {
                        identical = false;
                        break;
                    }
                }

                // If the values are not identical, add the row to the results
                if (!identical) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("RowIndex", rowIndex);
                    result.put("occurrences in csv1", 1);
                    result.put("occurrences in csv2", 1);
                    result.put("Identical?", false);
                    result.put("NonIdentical columns", new HashMap<>());
                    result.put("csv1 record", record1);
                    result.put("csv2 record", record2);
                    for (int i = 0; i < record1.size(); i++) {
                        if (!record1.get(i).equals(record2.get(i))) {
//                            result.get("NonIdentical columns").add("column " + (i + 1), new HashMap<>());
//                            result.get("NonIdentical columns").get("column " + (i + 1)).put("csv1 value", record1.get(i));
//                            result.get("NonIdentical columns").get("column " + (i + 1)).put("csv2 value", record2.get(i));
                        }
                    }
                    results.add(result);
                }
            }

            }
        }

        // Print the results
        PrintWriter writer = new PrintWriter(System.out);
        writer.println(results);
        System.out.println(results);
        writer.close();
    }
}

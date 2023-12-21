package com.javaexamples;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ComparisonExample {

    public static CSVRecord removeColumns(CSVRecord record, String... columnsToRemove) throws IOException {
    List<String> headers = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (String header : record.getParser().getHeaderNames()) {
            headers.add(header);
        }

        for (String value : record) {
            values.add(value);
        }

        for (String column : columnsToRemove) {
            int index = headers.indexOf(column);
            if (index != -1) {
                headers.remove(index);
                values.remove(index);
            }
        }

        StringWriter writer = new StringWriter();
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers.toArray(new String[headers.size()])));

        printer.printRecord(values);
        printer.flush();
        printer.close();

        String csvData = writer.toString();
        CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        return parser.iterator().next();
    }

    public static List<CSVRecord> readCSV(File fileName, String ... excludeColumns) throws IOException {

        Reader csvReader = new FileReader(fileName);

        CSVParser csvParser = CSVParser.parse(csvReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        List<CSVRecord> trimRecords = new ArrayList<>();

        for(CSVRecord record : csvParser) {
            CSVRecord trimRecord = removeColumns(record, excludeColumns);
            trimRecords.add(trimRecord);
        }

//        for(CSVRecord record: trimRecords) {
//            System.out.println(fileName);
//            System.out.println(record.getParser().getHeaderNames());
//            System.out.println(Arrays.stream(record.values()).collect(Collectors.toList()));
//        }

        return trimRecords;

    }

    private static Map<String, List<CSVRecord>> filterAndSortRows(List<CSVRecord> records) {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, List<CSVRecord>> filteredRows = new HashMap<>();

        for(CSVRecord record : records) {
            String originDestinationCabin = record.get("origin_airport_code") + record.get("destination_airport_code") + "-" + record.get("cabin").replaceAll("\\s", "");
            List<CSVRecord>stgMatchingRows = filteredRows.getOrDefault(originDestinationCabin, new ArrayList<>());
            stgMatchingRows.add(record);
            filteredRows.put(originDestinationCabin, stgMatchingRows);
        }
        //Sort rows by offer departure data column
        filteredRows.forEach((originDestinationCabin, stgMatchingRows) ->
                stgMatchingRows.sort(Comparator.comparing(record -> LocalDate.parse(record.get("offer_departure_start_date"), DATE_FORMATTER))));

        return filteredRows;
    }

    private static boolean areRowsIdentical(List<CSVRecord> stgRecords, List<CSVRecord>prodRecords){
        if(stgRecords.size() != prodRecords.size()) {
            return false;
        }
        for(int index =0; index<stgRecords.size(); index++) {

            CSVRecord stgRecord = stgRecords.get(index);
            CSVRecord prodRecord = prodRecords.get(index);

            String[] stgVal = stgRecord.values();
            String[] prodVal = prodRecord.values();

            System.out.println(stgVal.equals(prodVal));

            if(!stgRecord.equals(prodRecord)) {
                return false;
            }
        }

        return true;

    }


    public static void main(String[] args) throws IOException {

        File stgCsv = new File("/Users/stephensam/projects/MP/javaexamples/src/main/resources/stg.csv");
        List<CSVRecord> trimStgRecords = readCSV(stgCsv, "offer_id", "last_modified");

        File prodCsv = new File("/Users/stephensam/projects/MP/javaexamples/src/main/resources/prod.csv");
        List<CSVRecord> trimProdRecords = readCSV(prodCsv, "offer_id", "last_modified");

        List<String> originDestinationToVerify = List.of("SYDMEL-Y", "JFKADL-J");

//        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        private static final List<String> OCODE_DCODE_VALUES = List.of("ABCDEF", "XYZABC");


        Map<String, List<CSVRecord>> stgFilteredRows = filterAndSortRows(trimStgRecords);
        Map<String, List<CSVRecord>> prodFilteredRows = filterAndSortRows(trimProdRecords);

        //Compare rows to be identical!
        for(String originDestination : originDestinationToVerify) {
            List<CSVRecord> stgRows = stgFilteredRows.getOrDefault(originDestination, new ArrayList<>());
            List<CSVRecord> prodRows = prodFilteredRows.getOrDefault(originDestination, new ArrayList<>());

            //Check if rows are identical
            boolean rowsIdentical = areRowsIdentical(stgRows, prodRows);

            System.out.println("\nOriginDestinationCabin: " + originDestination);
            System.out.println("Occurrences in STG: " + stgRows.size());
            System.out.println("Occurrences in PROD: " + prodRows.size());
            System.out.println("Rows Identical?: " + rowsIdentical + "\n");
        }


    }
}

package com.javaexamples;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVRecordExample {

    public static CSVRecord removeColumns(CSVRecord record, String... columnsToRemove) throws IOException {
        List<String> headers = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (String header : record) {
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

    public static void main(String[] args) throws IOException {

        File prodCsv = new File("/Users/stephensam/projects/MP/javaexamples/src/main/resources/prod.csv");
        File stgCsv = new File("/Users/stephensam/projects/MP/javaexamples/src/main/resources/stg.csv");
        Reader prodCsvReader = new FileReader(prodCsv);
        Reader stgCsvReader = new FileReader(stgCsv);

//        String csvData = "header1,header2,header3,header4\nvalue1,value2,value3,value4";
        CSVParser prodParser = CSVParser.parse(prodCsvReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        CSVParser stgParser = CSVParser.parse(stgCsvReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        CSVRecord prodRecord = prodParser.iterator().next();
        CSVRecord stgRecord = stgParser.iterator().next();

        CSVRecord prodModifiedRecord = removeColumns(prodRecord, "offer_id", "last_modified");
        CSVRecord stgModifiedRecord = removeColumns(stgRecord, "offer_id", "last_modified");
        System.out.println("Headers prod\n");
        System.out.println(prodModifiedRecord.getParser().getHeaderNames());
        System.out.println("Values prod\n");
        System.out.println(prodModifiedRecord.values());

        System.out.println("stg\n");
        System.out.println(stgModifiedRecord);
    }
}

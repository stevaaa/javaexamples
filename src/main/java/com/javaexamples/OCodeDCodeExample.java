package com.javaexamples;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OCodeDCodeExample {

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

    public static void main(String[] args) throws IOException {
        String csvData = "ocode,dcode,course,startdate,enddate,cost\n" +
                "1001,2001,Java,2021-10-01,2021-12-15,1000\n" +
                "1002,2002,Python,2021-11-01,2021-12-31,1200\n" +
                "1003,2003,C++,2021-12-01,2022-02-28,1500\n";

        CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        List<CSVRecord> modifiedRecords = new ArrayList<>();

        for (CSVRecord record : parser) {
            CSVRecord modifiedRecord = removeColumns(record, "ocode", "dcode");
            modifiedRecords.add(modifiedRecord);
        }

        for (CSVRecord record : modifiedRecords) {
//            System.out.println(record);
            System.out.println(record.getParser().getHeaderNames());
            System.out.println(Arrays.stream(record.values()).collect(Collectors.toList()));
//            System.out.println(record.getRecordNumber());
//            record.parser.headers.headerNames
//            record.values
//            record.parser.recordNumber
        }
    }
}

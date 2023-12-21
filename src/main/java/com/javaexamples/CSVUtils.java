//package com.javaexamples;
/////Users/stephensam/projects/MP/javaexamples/src/main/resources/students2.csv
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;
//
//public class CSVUtils {
//
//    public static void main(String[] args) throws IOException {
//        // Read the CSV files
//        File students1File = new File("/Users/stephensam/projects/MP/javaexamples/src/main/resources/students1.csv");
//        File students2File = new File("/Users/stephensam/projects/MP/javaexamples/src/main/resources/students2.csv");
//        Reader students1Reader = new FileReader(students1File);
//        Reader students2Reader = new FileReader(students2File);
//        CSVParser students1Parser = new CSVParser(students1Reader, CSVFormat.DEFAULT);
//        CSVParser students2Parser = new CSVParser(students2Reader, CSVFormat.DEFAULT);
//
//        // Get the list of students in both files
//        List<String> students1 = new ArrayList<>();
//        List<String> students2 = new ArrayList<>();
//        for (CSVRecord record : students1Parser) {
//            students1.add(record.get("name"));
//        }
//        for (CSVRecord record : students2Parser) {
//            students2.add(record.get("name"));
//        }
//
//        // Find the students that are in both files
//        List<String> commonStudents = new ArrayList<>();
//        for (String student : students1) {
//            if (students2.contains(student)) {
//                commonStudents.add(student);
//            }
//        }
//
//        // Compare the number of rows in the files
//        int numRows1 = students1Parser.getRecords().size();
//        int numRows2 = students2Parser.getRecords().size();
//        if (numRows1 != numRows2) {
//            System.out.println("The number of rows in the files is not the same");
//            return;
//        }
//
//        // Compare the data in the files
//        for (String student : commonStudents) {
////            CSVRecord record1 = students1Parser.nextRecord();
////            CSVRecord record2 = students2Parser.nextRecord();
////            CSVRecord filteredRecord1 = removeColumns(record1, 2);
////            CSVRecord filteredRecord2 = removeColumns(record2, 2);
//            for (int i = 0; i < filteredRecord1.size(); i++) {
//                if (!filteredRecord1.get(i).equals(filteredRecord2.get(i))) {
//                    System.out.println("The data in the files is not the same for student " + student);
//                    return;
//                }
//            }
//        }
//
//        // The data in the files is the same
//        System.out.println("The data in the files is the same");
//    }
//
////    private static CSVRecord removeColumns(CSVRecord record, int numColumns) {
////        List<String> values = new ArrayList<>();
////        for (int i = 0; i < record.size(); i++) {
////            if (i < numColumns) {
////                continue;
////            }
////            values.add(record.get(i));
////        }
////        return new CSVRecord(values);
////    }
//}

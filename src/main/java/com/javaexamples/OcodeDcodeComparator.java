//package com.javaexamples;
//
//import org.apache.commons.csv.CSVRecord;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.ArrayList;
//
//public class OcodeDcodeComparator {
//
//    public static Map<String, List<Map<String, CSVRecord>>> areRowsIdentical(List<CSVRecord> discountRows, List<CSVRecord> actualRows) {
//        Map<String, List<Map<String, CSVRecord>>> result = new HashMap<>();
//
//        for (CSVRecord discountRow : discountRows) {
//            String ocodeDcode = discountRow.get("ocode") + discountRow.get("dcode");
//            List<Map<String, CSVRecord>> matchingRows = new ArrayList<>();
//
//            for (CSVRecord actualRow : actualRows) {
//                if (areIdentical(discountRow, actualRow)) {
//                    Map<String, CSVRecord> matchingRowPair = new HashMap<>();
//                    matchingRowPair.put("discountRow", discountRow);
//                    matchingRowPair.put("actualRow", actualRow);
//                    matchingRows.add(matchingRowPair);
//                }
//            }
//
//            result.put(ocodeDcode, matchingRows);
//        }
//
//        return result;
//    }
//
//    public static boolean areIdentical(CSVRecord record1, CSVRecord record2) {
//        if (record1.size() != record2.size()) {
//            return false;
//        }
//
//        for (String header : record1) {
//            if (!record1.get(header).equals(record2.get(header))) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public static void main(String[] args) {
//        // Sample discount.csv and actual.csv records
//        CSVRecord discountRow1 = CSVRecord.of("1001", "2001", "10", "20");
//        CSVRecord discountRow2 = CSVRecord.of("1002", "2002", "30", "40");
//        CSVRecord discountRow3 = CSVRecord.of("1003", "2003", "50", "60");
//
//        CSVRecord actualRow1 = CSVRecord.of("1001", "2001", "10", "20");
//        CSVRecord actualRow2 = CSVRecord.of("1002", "2002", "30", "40");
//        CSVRecord actualRow3 = CSVRecord.of("1003", "2003", "70", "80");
//
//        List<CSVRecord> discountRows = List.of(discountRow1, discountRow2, discountRow3);
//        List<CSVRecord> actualRows = List.of(actualRow1, actualRow2, actualRow3);
//
//        Map<String, List<Map<String, CSVRecord>>> result = areRowsIdentical(discountRows, actualRows);
//
//        for (String ocodeDcode : result.keySet()) {
//            System.out.println("ocode+dcode: " + ocodeDcode);
//
//            List<Map<String, CSVRecord>> matchingRows = result.get(ocodeDcode);
//            for (Map<String, CSVRecord> matchingRowPair : matchingRows) {
//                System.out.println("discountRow: " + matchingRowPair.get("discountRow"));
//                System.out.println("actualRow: " + matchingRowPair.get("actualRow"));
//            }
//
//            System.out.println();
//        }
//    }
//}

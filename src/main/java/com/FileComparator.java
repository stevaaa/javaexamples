import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileComparator {
    public static void main(String[] args) {
        String file1Path = "file1.csv";
        String file2Path = "file2.csv";

        List<String[]> file1Data = readData(file1Path);
        List<String[]> file2Data = readData(file2Path);

        int primaryKeyIndex = 1;  // Index of the primary key column

        Map<String, String[]> file2DataMap = new HashMap<>();
        for (String[] row : file2Data) {
            file2DataMap.put(row[primaryKeyIndex], row);
        }

        List<String[]> missingRows = new ArrayList<>();
        List<String[]> mismatchedRows = new ArrayList<>();

        for (String[] file1Row : file1Data) {
            String primaryKey = file1Row[primaryKeyIndex];
            if (!file2DataMap.containsKey(primaryKey)) {
                missingRows.add(file1Row);
            } else if (!Arrays.equals(file1Row, file2DataMap.get(primaryKey))) {
                mismatchedRows.add(file1Row);
            }
        }

        System.out.println("Missing Rows:");
        for (String[] row : missingRows) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println("Mismatched Rows:");
        for (String[] row : mismatchedRows) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static List<String[]> readData(String filePath) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}

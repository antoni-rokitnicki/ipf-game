package com.ipf.automaticcarsgame.csvparser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvParser {

    public int[][] pareseCsv(InputStream inputStream) throws IOException {
        final CSVParser csvParser = CSVFormat.DEFAULT.parse(new InputStreamReader(inputStream));
        final List<CSVRecord> records = csvParser.getRecords();
        return records.stream().map(row -> {
            final int[] convertedRow = new int[row.size()];
            for (int i = 0; i < row.size(); i++) {
                convertedRow[i] = Integer.parseInt(row.get(i));
            }
            return convertedRow;
        }).toArray(int[][]::new);
    }

}

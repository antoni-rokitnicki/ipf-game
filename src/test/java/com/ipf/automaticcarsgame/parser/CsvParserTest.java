package com.ipf.automaticcarsgame.parser;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvParserTest {
    private static final String CORRECT_CSV = "correct_csv.csv";
    private static final String INCORRECT_CSV = "incorrect_csv.csv";
    private CsvParser csvParser = new CsvParser();

    @Test
    public void shouldParseCsv() throws URISyntaxException, IOException {
        // given
        final InputStream inputStream = ClassLoader.getSystemResource(CORRECT_CSV).openStream();

        // when
        final int[][] result = csvParser.parse(inputStream);

        // then
        assertThat(result).contains(new int[][]{
                {1, 0, 0},
                {1, 1, 0},
                {1, 1, 0}
        });
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenInvalidFile() throws URISyntaxException, IOException {
        // given
        final InputStream inputStream = ClassLoader.getSystemResource(INCORRECT_CSV).openStream();

        // when
        final int[][] result = csvParser.parse(inputStream);

        // then
        // throw exception
    }


}
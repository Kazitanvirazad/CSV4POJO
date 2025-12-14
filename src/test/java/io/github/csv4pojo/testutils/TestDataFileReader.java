package io.github.csv4pojo.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.csv4pojo.common.CommonConstants.SPLIT_REGEX;

public class TestDataFileReader {
    private final static ObjectMapper mapper = new ObjectMapper();

    public static <T> Stream<T> readFile(String resourcePath, Function<String[], T> function) {
        try {
            InputStream inputStream = TestDataFileReader.class.getResourceAsStream(resourcePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines()
                    .map(line -> line.split(SPLIT_REGEX))
                    .map(function)
                    .onClose(() -> {
                        try {
                            reader.close();
                            inputStream.close();
                        } catch (IOException exception) {
                            Logger.getLogger(TestDataFileReader.class.getTypeName()).log(Level.SEVERE, exception.getMessage());
                        }
                    });
        } catch (Exception exception) {
            return Stream.empty();
        }
    }

    public static <T> List<T> readFile(Function<String[], T> function, String resourcePath) {
        Stream<T> fileDataStream = readFile(resourcePath, function);
        if (null != fileDataStream) {
            return fileDataStream.collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public static <T> List<T> readJson(String resourcePath, Class<T> clazz) {
        try (InputStream inputStream = TestDataFileReader.class.getResourceAsStream(resourcePath)) {
            return mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception exception) {
            return new ArrayList<>();
        }
    }
}

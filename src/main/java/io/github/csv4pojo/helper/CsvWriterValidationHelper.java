package io.github.csv4pojo.helper;

import io.github.csv4pojo.exception.CsvParsingException;
import io.github.csv4pojo.exception.MisConfiguredClassException;
import io.github.csv4pojo.exception.StreamException;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Kazi Tanvir Azad
 */
public class CsvWriterValidationHelper {
    public static void validateCSVOutputStream(Class<?> clazz, Stream<?> pojoStream, OutputStream outputStream) {
        validateOutputStream(outputStream);
        validateClass(clazz);
        validatePojoStream(pojoStream);
    }

    public static void validateCSVOutputStream(Class<?> clazz, List<?> pojoList, OutputStream outputStream) {
        validateOutputStream(outputStream);
        validateClass(clazz);
        validatePojoList(pojoList);
    }

    public static void validateCSVOutputStream(Class<?> clazz, OutputStream outputStream) {
        validateOutputStream(outputStream);
        validateClass(clazz);
    }

    private static void validatePojoList(List<?> pojoList) {
        if (null == pojoList) {
            throw new CsvParsingException("Pojo list argument is invalid or null");
        }
    }

    private static void validatePojoStream(Stream<?> pojoStream) {
        if (null == pojoStream) {
            throw new CsvParsingException("Pojo stream argument is invalid or null");
        }
    }

    private static void validateOutputStream(OutputStream outputStream) {
        if (null == outputStream) {
            throw new StreamException("OutputStream argument is invalid or null");
        }
    }

    private static void validateClass(Class<?> clazz) {
        if (null == clazz) {
            throw new MisConfiguredClassException("Class argument is invalid or null");
        }
    }
}

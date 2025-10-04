package io.github.csv4pojo.helper;

import io.github.csv4pojo.exception.StreamException;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

public class CSVWriterValidationHelper {
    public static <T> void validateCSVOutputStream(Class<T> clazz, Stream<T> pojoStream, OutputStream outputStream) {
        validateOutputStream(outputStream);
        validateClass(clazz);
        validatePojoStream(pojoStream);
    }

    public static <T> void validateCSVOutputStream(Class<T> clazz, List<T> pojoList, OutputStream outputStream) {
        validateOutputStream(outputStream);
        validateClass(clazz);
        validatePojoList(pojoList);
    }

    public static <T> void validateCSVOutputStream(Class<T> clazz, OutputStream outputStream) {
        validateOutputStream(outputStream);
        validateClass(clazz);
    }

    private static <T> void validatePojoList(List<T> pojoList) {
        if (null == pojoList) {
            throw new StreamException("Pojo list argument is invalid or null");
        }
    }

    private static <T> void validatePojoStream(Stream<T> pojoStream) {
        if (null == pojoStream) {
            throw new StreamException("Pojo stream argument is invalid or null");
        }
    }

    private static <T> void validateOutputStream(OutputStream outputStream) {
        if (null == outputStream) {
            throw new StreamException("OutputStream argument is invalid or null");
        }
    }

    private static <T> void validateClass(Class<T> clazz) {
        if (null == clazz) {
            throw new StreamException("Class argument is invalid or null");
        }
    }
}

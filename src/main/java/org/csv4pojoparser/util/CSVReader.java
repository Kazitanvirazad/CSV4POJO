package org.csv4pojoparser.util;

import org.csv4pojoparser.annotation.FieldType;

import java.io.InputStream;
import java.util.stream.Stream;

/**
 * @author Kazi Tanvir Azad
 */
public interface CSVReader {

    /**
     * Creates and returns Stream of Java objects mapped with {@link FieldType} annotation from CSV InputStream
     *
     * @param clazz       {@link Class<T>}
     * @param inputStream {@link InputStream}
     * @return Stream<T> {@link Stream<T>}
     */
    <T> Stream<T> createPojoStreamFromCSVInputStream(Class<T> clazz, InputStream inputStream);
}

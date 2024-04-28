package org.csv4pojoparser.util;

import org.csv4pojoparser.annotation.FieldType;

import java.io.InputStream;
import java.util.List;

/**
 * @author Kazi Tanvir Azad
 */
public interface CSVReader {

    /**
     * Creates and returns List of Java objects mapped with {@link FieldType} annotation from CSV InputStream
     *
     * @param clazz       Class<T>
     * @param inputStream InputStream
     * @return List<T>
     */
    <T> List<T> createPojoListFromCSVInputStream(Class<T> clazz, InputStream inputStream);
}

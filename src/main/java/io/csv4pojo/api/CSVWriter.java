package io.csv4pojo.api;

import io.csv4pojo.annotation.FieldType;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Kazi Tanvir Azad
 */
public interface CSVWriter {

    /**
     * Writes List of Java object mapped with the given java class annotated with {@link FieldType} annotation field
     * values in to the outputStream
     *
     * @param clazz        {@link Class<T>}
     * @param pojoList     {@link List<T>}
     * @param outputStream {@link OutputStream}
     */
    <T> void writeCSVOutputStream(Class<T> clazz, List<T> pojoList, OutputStream outputStream);

    /**
     * Writes Stream of Java object mapped with the given java class annotated with {@link FieldType} annotation field
     * values in to the outputStream
     *
     * @param clazz        {@link Class<T>}
     * @param pojoStream   {@link Stream<T>}
     * @param outputStream {@link OutputStream}
     */
    <T> void writeCSVOutputStream(Class<T> clazz, Stream<T> pojoStream, OutputStream outputStream);

    /**
     * Writes an empty csv file in to the OutputStream with all the headers mapped with the given java class annotated
     * with {@link FieldType} annotation field names
     *
     * @param clazz        {@link Class<T>}
     * @param outputStream {@link OutputStream}
     */
    <T> void writeCSVOutputStream(Class<T> clazz, OutputStream outputStream);
}

package org.csv4pojoparser.util;

import java.io.OutputStream;
import java.util.List;

/**
 * @author Kazi Tanvir Azad
 */
public interface CSVWriter {

    /**
     * Creates csv outputStream with all the Java object List data mapped with the given java class annotated fields
     *
     * @param clazz        Class<T>
     * @param pojoList     List<T>
     * @param outputStream OutputStream
     */
    <T> void createCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList, OutputStream outputStream);

    /**
     * Creates an empty csv file with all the headings mapped with the given java class annotated fields
     *
     * @param clazz        Class<T>
     * @param outputStream OutputStream
     */
    <T> void createEmptyCSVOutputStreamFromClass(Class<T> clazz, OutputStream outputStream);
}

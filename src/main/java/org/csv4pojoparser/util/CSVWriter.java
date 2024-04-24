package org.csv4pojoparser.util;

import java.io.OutputStream;
import java.util.List;

/**
 * @author Kazi Tanvir Azad
 */
public interface CSVWriter {

    <T> void createCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList, OutputStream outputStream);

    <T> void createEmptyCSVOutputStreamFromClass(Class<T> clazz, OutputStream outputStream);
}

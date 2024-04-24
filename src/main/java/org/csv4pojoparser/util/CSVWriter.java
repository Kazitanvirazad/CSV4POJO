package org.csv4pojoparser.util;

import java.io.Writer;
import java.util.List;

/**
 * @author Kazi Tanvir Azad
 */
public interface CSVWriter {

    <T> void createCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList, Writer writer);

    <T> void createEmptyCSVOutputStreamFromClass(Class<T> clazz, Writer writer);
}

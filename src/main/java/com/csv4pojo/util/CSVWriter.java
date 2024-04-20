package com.csv4pojo.util;

import java.io.Writer;
import java.util.List;

public interface CSVWriter {

    <T> void createCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList, Writer writer);

    <T> void createEmptyCSVOutputStreamFromClass(Class<T> clazz, Writer writer);
}

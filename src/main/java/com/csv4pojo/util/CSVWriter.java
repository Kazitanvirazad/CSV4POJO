package com.csv4pojo.util;

import java.io.OutputStream;
import java.util.List;

public interface CSVWriter {

    <T> OutputStream createCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList);

    <T> OutputStream createEmptyCSVOutputStreamFromClass(Class<T> clazz);
}

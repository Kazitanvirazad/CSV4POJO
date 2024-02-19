package com.csv4pojo.util.impl;

import com.csv4pojo.util.CSVWriter;

import java.io.OutputStream;
import java.util.List;

public class CSVWriterImpl implements CSVWriter {
    @Override
    public <T> OutputStream createCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList) {
        return null;
    }

    @Override
    public <T> OutputStream createEmptyCSVOutputStreamFromClass(Class<T> clazz) {
        return null;
    }
}

package com.csv4pojo.util;

import java.io.InputStream;
import java.util.List;

public interface CSVReader {

    <T> List<T> createPojoListFromCSVInputStream(Class<T> clazz, InputStream inputStream);
}

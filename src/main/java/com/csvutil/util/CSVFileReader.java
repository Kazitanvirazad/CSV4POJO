package com.csvutil.util;

import java.util.List;

public interface CSVFileReader {

	<T> List<T> createBeansFromCSV(Class<?> clazz, String path, String fileName);
}

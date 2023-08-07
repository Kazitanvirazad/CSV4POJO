package com.csvutil.util;

import java.util.List;

public interface CSVFileWriter {

	<T> void writeBeansToCSV(Class<T> clazz, List<T> beanList, String path, String fileName);

	<T> void createEmptyCSVFromClass(Class<T> clazz, String path, String fileName);
}

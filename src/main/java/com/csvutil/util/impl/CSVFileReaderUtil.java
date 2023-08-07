package com.csvutil.util.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.csvutil.exception.FieldNotMatchedException;
import com.csvutil.util.CSVFileReader;
import com.csvutil.util.CSVUtil;

public class CSVFileReaderUtil implements CSVFileReader {

	private final CSVUtil csvUtil;

	private CSVFileReaderUtil() {
		super();
		this.csvUtil = CSVUtil.getInstance();
	}

	public static CSVFileReader getCSVFileReader() {
		return new CSVFileReaderUtil();
	}

	@Override
	public <T> List<T> createBeansFromCSV(Class<?> clazz, String path, String fileName) {

		List<T> beans = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(path + fileName + csvUtil.getFileExtension()))) {
			List<String> beanFieldList = csvUtil.getClassFields(clazz);
			if (csvUtil.validateFields(beanFieldList, reader.readLine().split(","))) {
				reader.lines().forEach((line) -> {
					List<String> valueList = Arrays.asList(line.split(","));
					beans.add(csvUtil.createBeanFromCSVdata(valueList, clazz));
				});
			} else {
				throw new FieldNotMatchedException("Fields does not match with input csv file");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return beans;
	}
}

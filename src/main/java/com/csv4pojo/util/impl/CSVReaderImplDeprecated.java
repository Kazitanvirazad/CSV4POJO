package com.csv4pojo.util.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.csv4pojo.exception.FieldNotMatchedException;
import com.csv4pojo.util.CSVReader;
import com.csv4pojo.util.CSV4Pojo;

public class CSVReaderImplDeprecated {

	private final CSV4Pojo csvUtil;

	private CSVReaderImplDeprecated() {
		super();
		this.csvUtil = CSV4Pojo.getInstance();
	}

	public static CSVReader getCSVFileReader() {
		return new CSVReaderImplDeprecated();
	}

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

package com.csvutil.util.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.csvutil.util.CSVFileWriter;
import com.csvutil.util.CSVUtil;

public class CSVFileWriterUtil implements CSVFileWriter {

	private final CSVUtil csvUtil;

	private CSVFileWriterUtil() {
		super();
		this.csvUtil = CSVUtil.getInstance();
	}

	public static CSVFileWriter getCSVFileWriter() {
		return new CSVFileWriterUtil();
	}

	@Override
	public <T> void writeBeansToCSV(Class<T> clazz, List<T> beanList, String path, String fileName) {
		if (beanList != null && beanList.size() > 0) {
			if (path == null) {
				path = csvUtil.getDefaultPath();
			}
			if (fileName == null) {
				fileName = clazz.getSimpleName() + csvUtil.getFileExtension();
			}

			try (BufferedWriter writer = new BufferedWriter(
					new FileWriter(new File(path + fileName + csvUtil.getFileExtension())))) {
				List<String> fieldNames = csvUtil.getClassFields(clazz);
				csvUtil.writeLinesToCSVFile(fieldNames, writer);
				for (T bean : beanList) {
					List<String> fieldValues = csvUtil.getClassFieldValues(bean);
					csvUtil.writeLinesToCSVFile(fieldValues, writer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public <T> void createEmptyCSVFromClass(Class<T> clazz, String path, String fileName) {
		if (path == null) {
			path = csvUtil.getDefaultPath();
		}
		if (fileName == null) {
			fileName = clazz.getSimpleName() + csvUtil.getFileExtension();
		}
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(new File(path + fileName + csvUtil.getFileExtension())))) {
			List<String> fieldNames = csvUtil.getClassFields(clazz);
			csvUtil.writeLinesToCSVFile(fieldNames, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

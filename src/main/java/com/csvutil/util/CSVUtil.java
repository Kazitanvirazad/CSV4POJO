package com.csvutil.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;
import com.csvutil.exception.FieldNotMatchedException;

public class CSVUtil {

	public CSVUtil() {
		super();
	}

	public static CSVUtil getInstance() {
		return new CSVUtil();
	}

	private <T> List<String> getClassFields(Class<?> clazz) {
		List<String> fieldNames = new ArrayList<>();
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(FieldType.class)) {
					if (field.getAnnotation(FieldType.class).value() == Type.CLASS) {
						fieldNames.addAll(getClassFields(field.getType()));
					} else {
						fieldNames.add(field.getName());
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return fieldNames;
	}

	private <T> List<String> getClassFieldValues(T bean) {
		List<String> fieldValues = new ArrayList<>();
		try {
			for (Field field : bean.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(FieldType.class)) {
					field.setAccessible(true);
					if (field.getAnnotation(FieldType.class).value() == Type.CLASS) {
						fieldValues.addAll(getClassFieldValues(field.get(bean)));
					} else {
						String val = String.valueOf(field.get(bean));
						fieldValues.add(val);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return fieldValues;
	}

	public <T> void writeBeansToCSV(Class<T> clazz, List<T> beanList, String path, String fileName,
			String fileExtension) {
		if (beanList != null && beanList.size() > 0) {
			if (path == null) {
				path = System.getProperty("user.dir") + "//";
			}
			if (fileExtension == null) {
				fileExtension = ".csv";
			}
			if (fileName == null) {
				fileName = clazz.getSimpleName() + fileExtension;
			}

			try (BufferedWriter writer = new BufferedWriter(
					new FileWriter(new File(path + fileName + fileExtension)))) {
				List<String> fieldNames = getClassFields(clazz);
				writeLinesToCSVFile(fieldNames, writer);
				for (T bean : beanList) {
					List<String> fieldValues = getClassFieldValues(bean);
					writeLinesToCSVFile(fieldValues, writer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public <T> void createEmptyCSVFromClass(Class<T> clazz, String path, String fileName, String fileExtension) {
		if (path == null) {
			path = System.getProperty("user.dir") + "//";
		}
		if (fileExtension == null) {
			fileExtension = ".csv";
		}
		if (fileName == null) {
			fileName = clazz.getSimpleName() + fileExtension;
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + fileName + fileExtension)))) {
			List<String> fieldNames = getClassFields(clazz);
			writeLinesToCSVFile(fieldNames, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean validateFields(List<String> beanFields, String[] fileField) {
		if (beanFields.size() != fileField.length) {
			return false;
		}
		boolean flag = true;
		for (int i = 0; i < beanFields.size() && flag; i++) {
			if (!beanFields.get(i).equals(fileField[i])) {
				flag = false;
			}
		}
		return flag;
	}

	public <T> List<T> createBeansFromCSV(Class<?> clazz, String path, String fileName, String fileExtension) {
		List<T> beans = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(path + fileName + fileExtension))) {
			List<String> beanFieldList = getClassFields(clazz);
			if (validateFields(beanFieldList, reader.readLine().split(","))) {
				reader.lines().forEach((line) -> {
					String[] values = line.split(",");
					beans.add(createBeanFromCSVdata(values, clazz));
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

	@SuppressWarnings("unchecked")
	private <T> T createBeanFromCSVdata(String[] values, Class<?> clazz) {
		T beanInstance = null;
		try {
			beanInstance = (T) clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length && fields.length <= values.length; i++) {
			try {
				Field field = fields[i];
				if (beanInstance != null) {
					if (field.isAnnotationPresent(FieldType.class)) {
						field.setAccessible(true);
						FieldType type = field.getAnnotation(FieldType.class);
						if (type != null) {
							switch (type.value()) {
							case INTEGER:
								int intVal = Integer.valueOf(values[i]).intValue();
								field.set(beanInstance, intVal);
								break;
							case CHAR:
								char charVal = values[i].length() == 1 ? values[i].charAt(0) : '\000';
								field.set(beanInstance, charVal);
								break;
							case BOOLEAN:
								boolean booleanVal = Boolean.valueOf(values[i]);
								field.set(beanInstance, booleanVal);
								break;
							case FLOAT:
								float floatVal = Float.valueOf(values[i]);
								field.set(beanInstance, floatVal);
								break;
							case LONG:
								long longVal = Long.valueOf(values[i]);
								field.set(beanInstance, longVal);
								break;
							case DOUBLE:
								double doubleVal = Double.valueOf(values[i]);
								field.set(beanInstance, doubleVal);
								break;
							case STRING:
								String stringVal = String.valueOf(values[i]);
								field.set(beanInstance, stringVal);
								break;
							case CLASS:
								field.set(beanInstance, createBeanFromCSVdata(values, field.getType()));
								break;
							default:
								break;
							}
						} else {
							if (field.getType() == String.class)
								field.set(beanInstance, null);
						}
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return beanInstance;
	}

	private void writeLinesToCSVFile(List<String> contents, BufferedWriter writer) throws IOException {
		for (int i = 0; i < contents.size(); i++) {
			writer.write(contents.get(i));
			if (contents.size() == (i + 1)) {
				writer.newLine();
			} else {
				writer.write(",");
			}
		}
	}
}

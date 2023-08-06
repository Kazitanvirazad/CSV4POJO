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
import java.util.Arrays;
import java.util.List;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;
import com.csvutil.exception.FieldNotMatchedException;

public class CSVUtil {

	private final String fileExtension;

	private CSVUtil() {
		super();
		this.fileExtension = ".csv";
	}

	public static CSVUtil getInstance() {
		return new CSVUtil();
	}

	private <T> List<String> getClassFields(Class<?> clazz) {
		List<String> fieldNames = new ArrayList<>();
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(FieldType.class)) {
					if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
						fieldNames.addAll(getClassFields(field.getType()));
					} else {
						String fieldName = field.getDeclaredAnnotation(FieldType.class).csvColumnName().length() > 0
								? field.getDeclaredAnnotation(FieldType.class).csvColumnName()
								: field.getName();
						fieldNames.add(fieldName);
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
					if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
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

	public <T> void writeBeansToCSV(Class<T> clazz, List<T> beanList, String path, String fileName) {
		if (beanList != null && beanList.size() > 0) {
			if (path == null) {
				path = System.getProperty("user.dir") + "//";
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

	public <T> void createEmptyCSVFromClass(Class<T> clazz, String path, String fileName) {
		if (path == null) {
			path = System.getProperty("user.dir") + "//";
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

	public <T> List<T> createBeansFromCSV(Class<?> clazz, String path, String fileName) {
		List<T> beans = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(path + fileName + fileExtension))) {
			List<String> beanFieldList = getClassFields(clazz);
			if (validateFields(beanFieldList, reader.readLine().split(","))) {
				reader.lines().forEach((line) -> {
					List<String> valueList = Arrays.asList(line.split(","));
					beans.add(createBeanFromCSVdata(valueList, clazz));
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
	private <T> T createBeanFromCSVdata(List<String> valueList, Class<?> clazz) {
		T beanInstance = null;
		try {
			beanInstance = (T) clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		Field[] fields = clazz.getDeclaredFields();
		int i = 0;
		int fieldIndex = 0;
		while (fieldIndex < fields.length && fields.length <= valueList.size()) {
			try {
				Field field = fields[fieldIndex];
				if (beanInstance != null) {
					if (field.isAnnotationPresent(FieldType.class)) {
						field.setAccessible(true);
						FieldType type = field.getAnnotation(FieldType.class);
						if (type != null) {
							switch (type.dataType()) {
							case INTEGER:
								int intVal = Integer.valueOf(valueList.get(i)).intValue();
								field.setInt(beanInstance, intVal);
								break;
							case CHAR:
								char charVal = valueList.get(i).length() == 1 ? (Character) valueList.get(i).charAt(0)
										: '\000';
								field.set(beanInstance, charVal);
								break;
							case BOOLEAN:
								boolean booleanVal = Boolean.valueOf(String.valueOf(valueList.get(i)));
								field.set(beanInstance, booleanVal);
								break;
							case FLOAT:
								float floatVal = Float.valueOf(valueList.get(i));
								field.setFloat(beanInstance, floatVal);
								break;
							case LONG:
								long longVal = Long.valueOf(valueList.get(i));
								field.set(beanInstance, longVal);
								break;
							case DOUBLE:
								double doubleVal = Double.valueOf(valueList.get(i));
								field.set(beanInstance, doubleVal);
								break;
							case STRING:
								String stringVal = valueList.get(i);
								field.set(beanInstance, stringVal);
								break;
							case CLASSTYPE:
								field.set(beanInstance,
										createBeanFromCSVdata(valueList.subList(i, valueList.size()), field.getType()));
								i += getAnnotatedFieldCount(field.getType()) - 1;
								break;
							default:
								break;
							}
						}
						i++;
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
			fieldIndex++;
		}
		return beanInstance;
	}

	private int getAnnotatedFieldCount(Class<?> clazz) {
		int count = 0;
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(FieldType.class))
				count++;
		}
		return count;
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

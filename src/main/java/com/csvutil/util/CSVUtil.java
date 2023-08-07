package com.csvutil.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class CSVUtil {

	private final String fileExtension;

	private final String defaultPath;

	private CSVUtil() {
		super();
		this.fileExtension = ".csv";
		this.defaultPath = System.getProperty("user.dir") + "//";
	}

	public static CSVUtil getInstance() {
		return new CSVUtil();
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public String getDefaultPath() {
		return defaultPath;
	}

	public <T> List<String> getClassFields(Class<?> clazz) {
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

	public <T> List<String> getClassFieldValues(T bean) {
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

	public boolean validateFields(List<String> beanFields, String[] fileField) {
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

	@SuppressWarnings("unchecked")
	public <T> T createBeanFromCSVdata(List<String> valueList, Class<?> clazz) {
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

	public int getAnnotatedFieldCount(Class<?> clazz) {
		int count = 0;
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(FieldType.class))
				count++;
		}
		return count;
	}

	public void writeLinesToCSVFile(List<String> contents, BufferedWriter writer) throws IOException {
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

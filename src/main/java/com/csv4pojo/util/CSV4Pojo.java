package com.csv4pojo.util;

import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;
import com.csv4pojo.exception.MisConfiguredClassFieldException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CSV4Pojo {

    private CSV4Pojo() {
        super();
    }

    public static CSV4Pojo getInstance() {
        return new CSV4Pojo();
    }

    public <T> List<String> getClassFields(Class<?> clazz) {
        List<String> fieldNames = new ArrayList<>();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(FieldType.class)) {
                    if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
                        fieldNames.addAll(getClassFields(field.getType()));
                    } else {
                        String fieldName = getFieldName(field);
                        fieldNames.add(fieldName);
                    }
                }
            }
        } catch (RuntimeException exception) {
            throw new MisConfiguredClassFieldException("CSV4Pojo FieldType Annotations not properly set: " + exception.getMessage());
        }
        return fieldNames;
    }

    public <T> List<String> getClassFieldValues(T classObject) {
        List<String> fieldValues = new ArrayList<>();
        try {
            for (Field field : classObject.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(FieldType.class)) {
                    field.setAccessible(true);
                    if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
                        fieldValues.addAll(getClassFieldValues(field.get(classObject)));
                    } else {
                        //logic needs to be changed for handling arrays
                        String value = String.valueOf(field.get(classObject));
                        fieldValues.add(value);
                    }
                }
            }
        } catch (RuntimeException | IllegalAccessException exception) {
            throw new MisConfiguredClassFieldException("CSV4Pojo FieldType Annotations not properly set: " + exception.getMessage());
        }
        return fieldValues;
    }

    public boolean validateFields(List<String> csv4PojoAnnotatedClassFields, String[] csvHeaderElements) {
        if (csv4PojoAnnotatedClassFields.size() != csvHeaderElements.length) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < csv4PojoAnnotatedClassFields.size() && flag; i++) {
            flag = csv4PojoAnnotatedClassFields.get(i).equals(csvHeaderElements[i]);
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

    public void writeLinesToCSVFile(List<String> contents, BufferedWriter writer) throws IOException {
        for (int i = 0; i < contents.size(); i++) {
            //logic needs to be changed to handle comma and double quotes while writing data to csv file
            writer.write(contents.get(i));
            if (contents.size() == (i + 1)) {
                writer.newLine();
            } else {
                writer.write(",");
            }
        }
    }

    private int getAnnotatedFieldCount(Class<?> clazz) {
        int count = 0;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(FieldType.class))
                count++;
        }
        return count;
    }

    private String getFieldName(Field field) {
        return !field.getDeclaredAnnotation(FieldType.class).csvColumnName().isEmpty() ?
                field.getDeclaredAnnotation(FieldType.class).csvColumnName() : field.getName();
    }
}

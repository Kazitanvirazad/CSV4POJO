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

//    public <T> List<String> getClassFields(Class<?> clazz) {
//        List<String> fieldNames = new ArrayList<>();
//        try {
//            for (Field field : clazz.getDeclaredFields()) {
//                if (field.isAnnotationPresent(FieldType.class)) {
//                    if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
//                        fieldNames.addAll(getClassFields(field.getType()));
//                    } else {
//                        String fieldName = getFieldName(field);
//                        fieldNames.add(fieldName);
//                    }
//                }
//            }
//        } catch (RuntimeException exception) {
//            throw new MisConfiguredClassFieldException("CSV4Pojo FieldType Annotations not properly set: " + exception.getMessage());
//        }
//        return fieldNames;
//    }
//
//    public <T> List<String> getClassFieldValues(T classObject) {
//        List<String> fieldValues = new ArrayList<>();
//        try {
//            for (Field field : classObject.getClass().getDeclaredFields()) {
//                if (field.isAnnotationPresent(FieldType.class)) {
//                    field.setAccessible(true);
//                    if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
//                        fieldValues.addAll(getClassFieldValues(field.get(classObject)));
//                    } else {
//                        //logic needs to be changed for handling arrays
//                        String value = String.valueOf(field.get(classObject));
//                        fieldValues.add(value);
//                    }
//                }
//            }
//        } catch (RuntimeException | IllegalAccessException exception) {
//            throw new MisConfiguredClassFieldException("CSV4Pojo FieldType Annotations not properly set: " + exception.getMessage());
//        }
//        return fieldValues;
//    }
//
//
//    public void writeLinesToCSVFile(List<String> contents, BufferedWriter writer) throws IOException {
//        for (int i = 0; i < contents.size(); i++) {
//            //logic needs to be changed to handle comma and double quotes while writing data to csv file
//            writer.write(contents.get(i));
//            if (contents.size() == (i + 1)) {
//                writer.newLine();
//            } else {
//                writer.write(",");
//            }
//        }
//    }

}

package com.csv4pojo.util.impl;

import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.util.CSV4PojoUtils;
import com.csv4pojo.util.CSVWriter;
import com.csv4pojo.util.CommonConstants;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CSVWriterImpl implements CSVWriter, CommonConstants {
    @Override
    public <T> void createCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList, Writer writer) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            writeHeaderToCsvOutputStream(clazz, bufferedWriter);
            pojoList.forEach(pojo -> {

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public <T> void createEmptyCSVOutputStreamFromClass(Class<T> clazz, Writer writer) {

    }

    private <T> List<String> getAnnotatedFieldValuesFromPojo(T pojo) {
        Class<?> clazz = pojo.getClass();
        List<String> fieldvalues = new ArrayList<>();
        List<String> annotatedClassFields = CSV4PojoUtils.getAnnotatedClassFields(clazz);
        for (int i = 0; i < annotatedClassFields.size(); i++) {
            String fieldName = annotatedClassFields.get(i);
            try {
                Field field = clazz.getDeclaredField(fieldName);
                switch (field.getAnnotation(FieldType.class).dataType()) {
                    case INT:
                        int intVal = field.getInt(pojo);
                        fieldvalues.add(String.valueOf(intVal));
                        break;
                    case STRING:
                        String stringVal = (String) field.get(pojo);
                        fieldvalues.add(CSV4PojoUtils.requireNonNullElse(stringVal, ""));
                        break;
                    case BOOLEAN:
                        boolean boolVal = field.getBoolean(pojo);
                        fieldvalues.add(String.valueOf(boolVal));
                        break;
                    case FLOAT:
                        float floatVal = field.getFloat(pojo);
                        fieldvalues.add(String.valueOf(floatVal));
                        break;
                    case DOUBLE:
                        double doubleVal = field.getDouble(pojo);
                        fieldvalues.add(String.valueOf(doubleVal));
                        break;
                    case LONG:
                        long longVal = field.getLong(pojo);
                        fieldvalues.add(String.valueOf(longVal));
                        break;
                    case CHAR:
                        char charVal = field.getChar(pojo);
                        fieldvalues.add(String.valueOf(charVal));
                        break;
                    case CLASSTYPE:
                        Class<?> classValObj = (Class<?>) field.get(pojo);
                        List<String> classValList = getAnnotatedFieldValuesFromPojo(classValObj);
                        fieldvalues.addAll(classValList);
                        i += CSV4PojoUtils.getAnnotatedFieldCount(field.getType()) - 1;
                        break;
                    case INTEGER_ARRAY:
                        Integer[] intArrayVal = (Integer[]) field.get(pojo);
                        StringBuilder intArrayString = new StringBuilder();
                        for (int j = 0; j < intArrayVal.length; j++) {
                            Integer integer = intArrayVal[j];
                            intArrayString.append(integer);
                            if (j < intArrayVal.length - 1) {
                                intArrayString.append(COMMA);
                            }
                        }
                        fieldvalues.add(intArrayString.toString());
                        break;
                    case STRING_ARRAY:
                        break;
                    case BOOLEAN_ARRAY:
                        break;
                    case FLOAT_ARRAY:
                        break;
                    case DOUBLE_ARRAY:
                        break;
                    case LONG_ARRAY:
                        break;
                    case CHARACTER_ARRAY:
                        break;
                }
            } catch (NoSuchFieldException | IllegalAccessException exception) {

            }
        }
        return fieldvalues;
    }

    private <T> void writeHeaderToCsvOutputStream(Class<T> clazz, BufferedWriter writer) throws IOException {
        List<String> annotatedHeaderNameListFromClass = CSV4PojoUtils.getAnnotatedClassFields(clazz);
        writer.write(getFormattedLineElements(annotatedHeaderNameListFromClass));
        writer.newLine();
    }

    private String getFormattedLineElements(List<String> lineElements) {
        StringBuilder formattedLine = new StringBuilder();
        for (int i = 0; i < lineElements.size(); i++) {
            String element = lineElements.get(i);
            if (element.contains(ONE_DOUBLE_QUOTES) || element.contains(COMMA)) {
                element = element.replace(ONE_DOUBLE_QUOTES, TWO_DOUBLE_QUOTES);
                wrapToFormatElement(element);
            }
            formattedLine.append(element);
            if (i < lineElements.size() - 1) {
                formattedLine.append(COMMA);
            }
        }
        return formattedLine.toString();
    }

    private void wrapToFormatElement(String element) {
        element = ONE_DOUBLE_QUOTES + element + ONE_DOUBLE_QUOTES;
    }
}

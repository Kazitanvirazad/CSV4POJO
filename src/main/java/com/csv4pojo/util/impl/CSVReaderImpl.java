package com.csv4pojo.util.impl;

import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;
import com.csv4pojo.exception.CSVParsingException;
import com.csv4pojo.exception.FieldNotMatchedException;
import com.csv4pojo.exception.InputOutputStreamException;
import com.csv4pojo.exception.MisConfiguredClassFieldException;
import com.csv4pojo.util.CSVReader;
import com.csv4pojo.util.CommonConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReaderImpl implements CSVReader, CommonConstants {

    @Override
    public <T> List<T> createPojoListFromCSVInputStream(Class<T> clazz, InputStream inputStream) {
        List<T> pojoList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            // Getting the class field names with FieldType annotation
            List<String> annotatedClassFields = getAnnotatedClassFields(clazz);

            // Extracting the csv header elements from the first line of the CSV InputStream
            String csvHeader = reader.readLine();
            if (csvHeader == null) {
                throw new CSVParsingException("CSV header elements does not exists!");
            }

            // Split the line by comma, but ignore commas inside double quotes
            String[] headerElements = csvHeader.split(SPLIT_REGEX, -1);

            // Removes extra special characters such as double quotes added by MS Excel or Google Sheet
            cleanCsvLineElements(headerElements);

            // Validating csv header line elements with class fields
            if (!validateFields(annotatedClassFields, headerElements)) {
                throw new FieldNotMatchedException("CSV header elements does not match with Class fields");
            }

            // Reading each line after header and execute logic to create pojo list
            reader.lines().forEach(line -> {
                // Split the line by comma, but ignore commas inside double quotes
                String[] lineElements = line.split(SPLIT_REGEX, -1);

                // Removes extra special characters such as double quotes added by MS Excel or Google Sheet
                cleanCsvLineElements(lineElements);

                // Creating the object of the specified class with the field values passed as a List of String
                T pojo = createPojoFromCSVLineElements(Arrays.asList(lineElements), clazz);

                if (pojo != null) {
                    // Adding the returned pojo in to the list if the pojo is not null
                    pojoList.add(pojo);
                }
            });

            // Returning the resultant list
            return pojoList;
        } catch (IOException exception) {
            throw new InputOutputStreamException("InputStream is invalid or null: ", exception);
        }
    }

    private <T> T createPojoFromCSVLineElements(List<String> lineElements, Class<T> clazz) {
        T classInstance = null;
        try {
            classInstance = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException exception) {
            throw new MisConfiguredClassFieldException(exception.getMessage(), exception);
        }

        Field[] fields = clazz.getDeclaredFields();
        int i = 0;
        int fieldIndex = 0;
        while (fieldIndex < fields.length && fields.length <= lineElements.size()) {
            try {
                Field field = fields[fieldIndex];
                if (classInstance != null) {
                    if (field.isAnnotationPresent(FieldType.class)) {
                        field.setAccessible(true);
                        FieldType type = field.getAnnotation(FieldType.class);
                        if (type != null) {
                            switch (type.dataType()) {
                                case INTEGER:
                                    int intVal = Integer.valueOf(lineElements.get(i)).intValue();
                                    field.setInt(classInstance, intVal);
                                    break;
                                case CHAR:
                                    char charVal = lineElements.get(i).length() == 1 ? (Character) lineElements.get(i).charAt(0)
                                            : '\000';
                                    field.set(classInstance, charVal);
                                    break;
                                case BOOLEAN:
                                    boolean booleanVal = Boolean.valueOf(String.valueOf(lineElements.get(i)));
                                    field.set(classInstance, booleanVal);
                                    break;
                                case FLOAT:
                                    float floatVal = Float.valueOf(lineElements.get(i));
                                    field.setFloat(classInstance, floatVal);
                                    break;
                                case LONG:
                                    long longVal = Long.valueOf(lineElements.get(i));
                                    field.set(classInstance, longVal);
                                    break;
                                case DOUBLE:
                                    double doubleVal = Double.valueOf(lineElements.get(i));
                                    field.set(classInstance, doubleVal);
                                    break;
                                case STRING:
                                    String stringVal = lineElements.get(i);
                                    field.set(classInstance, stringVal);
                                    break;
                                case CLASSTYPE:
                                    field.set(classInstance,
                                            createPojoFromCSVLineElements(lineElements.subList(i, lineElements.size()), field.getType()));
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
        return classInstance;
    }

    /**
     * Removes extra special characters such as double quotes added by MS Excel or Google Sheet
     *
     * @param elements String[]
     */
    private void cleanCsvLineElements(String[] elements) {
        for (int i = 0; i < elements.length; i++) {
            String element = elements[i];

            // replacing two double quotes to one double quotes
            element = element.replace(TWO_DOUBLE_QUOTES, ONE_DOUBLE_QUOTES);

            // removing the wrapped double quotes in the beginning of the element
            if (element.length() > 1 && element.startsWith(ONE_DOUBLE_QUOTES)) {
                element = element.substring(1);
            }

            // removing the wrapped double quotes in the end of the element
            if (element.length() > 2 && element.endsWith(ONE_DOUBLE_QUOTES)) {
                element = element.substring(0, element.length() - 1);
            }

            // updating the array with formatted element
            elements[i] = element;
        }
    }

    /**
     * Validates class fields with an array of csv header elements. Returns true if
     * length and all values matches else returns false
     *
     * @param csv4PojoAnnotatedClassFields
     * @param csvHeaderElements
     * @return boolean
     */
    private boolean validateFields(List<String> csv4PojoAnnotatedClassFields, String[] csvHeaderElements) {
        if (csv4PojoAnnotatedClassFields.size() != csvHeaderElements.length) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < csv4PojoAnnotatedClassFields.size() && flag; i++) {
            flag = csv4PojoAnnotatedClassFields.get(i).equals(csvHeaderElements[i]);
        }
        return flag;
    }

    /**
     * Returns list of class field names which are annotated with FieldType annotation
     *
     * @param clazz
     * @return List<String>
     */
    private <T> List<String> getAnnotatedClassFields(Class<T> clazz) {
        List<String> fieldNames = new ArrayList<>();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(FieldType.class)) {
                    if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
                        fieldNames.addAll(getAnnotatedClassFields(field.getType()));
                    } else {
                        String fieldName = getFieldName(field);
                        fieldNames.add(fieldName);
                    }
                }
            }
        } catch (RuntimeException exception) {
            throw new MisConfiguredClassFieldException("CSV4Pojo FieldType Annotations not properly set: ", exception);
        }
        return fieldNames;
    }

    /**
     * Returns field names from csvColumnName attribute value if exists, else returns original field name
     *
     * @param field
     * @return String
     */
    private String getFieldName(Field field) {
        return !field.getDeclaredAnnotation(FieldType.class).csvColumnName().isEmpty() ?
                field.getDeclaredAnnotation(FieldType.class).csvColumnName() : field.getName();
    }
}

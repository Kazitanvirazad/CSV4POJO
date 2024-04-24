package org.csv4pojoparser.util.impl;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.exception.CSVParsingException;
import org.csv4pojoparser.exception.FieldNotMatchedException;
import org.csv4pojoparser.exception.InputOutputStreamException;
import org.csv4pojoparser.exception.MisConfiguredClassFieldException;
import org.csv4pojoparser.util.CSV4PojoUtils;
import org.csv4pojoparser.util.CSVReader;
import org.csv4pojoparser.util.CommonConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kazi Tanvir Azad
 */
public class CSVReaderImpl implements CSVReader, CommonConstants {

    @Override
    public <T> List<T> createPojoListFromCSVInputStream(Class<T> clazz, InputStream inputStream) {
        List<T> pojoList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            // Getting the class field names with FieldType annotation
            List<String> annotatedClassFields = CSV4PojoUtils.getAnnotatedClassFieldNames(clazz);

            // Extracting the csv header elements from the first line of the CSV InputStream
            String csvHeader = reader.readLine();
            if (csvHeader == null) {
                throw new CSVParsingException("CSV header elements does not exists!");
            }

            // Remove BOM (BYTE-ORDER MARK) if it's present in the line. For UTF-8 the BOM is: 0xEF, 0xBB, 0xBF
            csvHeader = removeUTF8BOM(csvHeader);

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
                T pojo = createPojoFromCSVLineElements(clazz, Arrays.asList(lineElements));

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

    private <T> T createPojoFromCSVLineElements(Class<T> clazz, List<String> lineElements) {
        T classInstance;
        try {
            classInstance = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException exception) {
            throw new MisConfiguredClassFieldException(exception.getMessage(), exception);
        }

        List<Field> fields = CSV4PojoUtils.getAnnotatedClassFieldList(clazz);

        int index = 0;
        int lineIndex = 0;
        while (index < fields.size()) {
            String lineElement = lineElements.get(lineIndex);
            Field field = fields.get(index);
            try {
                field.setAccessible(true);
                FieldType type = field.getAnnotation(FieldType.class);
                if (type != null) {
                    switch (type.dataType()) {
                        case INT:
                            int intVal = Integer.parseInt(lineElement);
                            field.setInt(classInstance, intVal);
                            break;
                        case INTEGER_ARRAY:
                            String[] rawIntegerValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Integer> integerValList = new ArrayList<>();
                            for (String s : rawIntegerValList) {
                                integerValList.add(Integer.parseInt(s));
                            }
                            field.set(classInstance,
                                    integerValList.toArray((Integer[]) Array.newInstance(field.getType().getComponentType(), integerValList.size())));
                            break;
                        case CHAR:
                            char charVal = lineElement.length() == 1 ? lineElement.charAt(0)
                                    : '\000';
                            field.set(classInstance, charVal);
                            break;
                        case CHARACTER_ARRAY:
                            String[] rawCharacterValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Character> characterValList = new ArrayList<>();
                            for (String s : rawCharacterValList) {
                                characterValList.add(s.charAt(0));
                            }
                            field.set(classInstance,
                                    characterValList.toArray((Character[]) Array.newInstance(field.getType().getComponentType(), characterValList.size())));
                            break;
                        case BOOLEAN:
                            boolean booleanVal = Boolean.parseBoolean(lineElement);
                            field.set(classInstance, booleanVal);
                            break;
                        case BOOLEAN_ARRAY:
                            String[] rawBooleanValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Boolean> booleanValList = new ArrayList<>();
                            for (String s : rawBooleanValList) {
                                booleanValList.add(Boolean.parseBoolean(s));
                            }
                            field.set(classInstance,
                                    booleanValList.toArray((Boolean[]) Array.newInstance(field.getType().getComponentType(), booleanValList.size())));
                            break;
                        case FLOAT:
                            float floatVal = Float.parseFloat(lineElement);
                            field.setFloat(classInstance, floatVal);
                            break;
                        case FLOAT_ARRAY:
                            String[] rawFloatValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Float> floatValList = new ArrayList<>();
                            for (String s : rawFloatValList) {
                                floatValList.add(Float.parseFloat(s));
                            }
                            field.set(classInstance,
                                    floatValList.toArray((Float[]) Array.newInstance(field.getType().getComponentType(), floatValList.size())));
                            break;
                        case LONG:
                            long longVal = Long.parseLong(lineElement);
                            field.set(classInstance, longVal);
                            break;
                        case LONG_ARRAY:
                            String[] rawLongValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Long> longValList = new ArrayList<>();
                            for (String s : rawLongValList) {
                                longValList.add(Long.parseLong(s));
                            }
                            field.set(classInstance,
                                    longValList.toArray((Long[]) Array.newInstance(field.getType().getComponentType(), longValList.size())));
                            break;
                        case DOUBLE:
                            double doubleVal = Double.parseDouble(lineElement);
                            field.set(classInstance, doubleVal);
                            break;
                        case DOUBLE_ARRAY:
                            String[] rawDoubleValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Double> doubleValList = new ArrayList<>();
                            for (String s : rawDoubleValList) {
                                doubleValList.add(Double.parseDouble(s));
                            }
                            field.set(classInstance,
                                    doubleValList.toArray((Double[]) Array.newInstance(field.getType().getComponentType(), doubleValList.size())));
                            break;
                        case STRING:
                            field.set(classInstance, lineElement);
                            break;
                        case STRING_ARRAY:
                            String[] rawStringValList = lineElement.split(SPLIT_REGEX, -1);
                            List<String> stringValList = Arrays.asList(rawStringValList);
                            field.set(classInstance,
                                    stringValList.toArray((String[]) Array.newInstance(field.getType().getComponentType(), stringValList.size())));
                            break;
                        case CLASSTYPE:
                            int fieldCount = CSV4PojoUtils.getAnnotatedFieldCount(field.getType());
                            field.set(classInstance,
                                    createPojoFromCSVLineElements(field.getType(), lineElements.subList(lineIndex, lineIndex + fieldCount)));
                            lineIndex += fieldCount - 1;
                            break;
                        default:
                            break;
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            index++;
            lineIndex++;
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
     * Remove BOM if it's present in the line. For UTF-8 the BOM is: 0xEF, 0xBB, 0xBF
     *
     * @param line
     * @return String
     */
    private String removeUTF8BOM(String line) {
        if (line.startsWith(UTF8_BOM)) {
            line = line.substring(1);
        }
        return line;
    }
}

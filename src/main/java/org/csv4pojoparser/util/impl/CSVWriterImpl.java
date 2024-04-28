package org.csv4pojoparser.util.impl;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.exception.CSVParsingException;
import org.csv4pojoparser.exception.InputOutputStreamException;
import org.csv4pojoparser.util.CSV4PojoUtils;
import org.csv4pojoparser.util.CSVWriter;
import org.csv4pojoparser.util.CommonConstants;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kazi Tanvir Azad
 */
public class CSVWriterImpl implements CSVWriter, CommonConstants {

    /**
     * Writes List of Java object mapped with the given java class annotated with {@link FieldType} annotation field
     * values in to the outputStream
     *
     * @param clazz        Class<T>
     * @param pojoList     List<T>
     * @param outputStream OutputStream
     */
    @Override
    public <T> void writeCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList, OutputStream outputStream) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream), CHAR_BUFFER_SIZE())) {
            // Writing the header elements to the OutputStream
            writeHeaderToOutputStream(clazz, writer);
            // Reading each object from List and execute logic to create line elements and write in to the BufferedWriter
            for (T pojo : pojoList) {
                // Skipping null objects from the list
                if (pojo != null) {
                    // Reading the annotated with @FieldType annotation field data from java object and putting it in to a List
                    List<String> lineElements = getAnnotatedFieldValuesFromPojo(pojo, pojo.getClass());

                    // Converting the List of line elements to a CSV row format
                    String formattedLineElement = getFormattedLineElements(lineElements);

                    // Writing the csv formatted row in to the BufferedWriter
                    writer.write(formattedLineElement);

                    // Appending a line in to the BufferedWriter
                    writer.newLine();
                }
            }
        } catch (IOException exception) {
            throw new InputOutputStreamException("OutputStream is invalid or null: ", exception);
        }
    }

    /**
     * Writes an empty csv file in to the OutputStream with all the headers mapped with the given java class annotated
     * with {@link FieldType} annotation field names
     *
     * @param clazz        Class<T>
     * @param outputStream OutputStream
     */
    @Override
    public <T> void writeEmptyCSVOutputStreamFromClass(Class<T> clazz, OutputStream outputStream) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            // Writing the header elements to the OutputStream
            writeHeaderToOutputStream(clazz, writer);
        } catch (IOException exception) {
            throw new InputOutputStreamException("OutputStream is invalid or null: ", exception);
        }
    }

    /**
     * Read all the annotated with {@link FieldType} annotation fields of the Java object passed in the method
     * parameter and creates and returns a List of the values
     *
     * @param pojo T
     * @return List<String>
     */
    private <T> List<String> getAnnotatedFieldValuesFromPojo(T pojo, Class<?> clazz) {
        List<String> fieldValues = new ArrayList<>();

        if (pojo == null) {
            int fieldCount = CSV4PojoUtils.getAnnotatedFieldCount(clazz);
            for (int i = 0; i < fieldCount; i++) {
                fieldValues.add(EMPTY_STRING);
            }
            return fieldValues;
        }

        List<Field> fields = CSV4PojoUtils.getAnnotatedClassFieldList(clazz);

        int index = 0;
        while (index < fields.size()) {
            Field field = fields.get(index);
            StringBuilder stringBuilder = new StringBuilder();
            try {
                field.setAccessible(true);
                FieldType fieldType = field.getAnnotation(FieldType.class);
                if (fieldType != null) {
                    switch (fieldType.dataType()) {
                        case INTEGER_ARRAY:
                            Integer[] integerArray = (Integer[]) field.get(pojo);
                            if (integerArray == null) {
                                fieldValues.add(EMPTY_STRING);
                                break;
                            }
                            for (int i = 0; i < integerArray.length; i++) {
                                stringBuilder.append(integerArray[i]);
                                if (i < integerArray.length - 1) {
                                    stringBuilder.append(COMMA);
                                }
                            }
                            fieldValues.add(stringBuilder.toString());
                            break;
                        case STRING_ARRAY:
                            String[] stringArray = (String[]) field.get(pojo);
                            if (stringArray == null) {
                                fieldValues.add(EMPTY_STRING);
                                break;
                            }
                            for (int i = 0; i < stringArray.length; i++) {
                                stringBuilder.append(stringArray[i]);
                                if (i < stringArray.length - 1) {
                                    stringBuilder.append(COMMA);
                                }
                            }
                            fieldValues.add(stringBuilder.toString());
                            break;
                        case BOOLEAN_ARRAY:
                            Boolean[] booleanArray = (Boolean[]) field.get(pojo);
                            if (booleanArray == null) {
                                fieldValues.add(EMPTY_STRING);
                                break;
                            }
                            for (int i = 0; i < booleanArray.length; i++) {
                                stringBuilder.append(booleanArray[i]);
                                if (i < booleanArray.length - 1) {
                                    stringBuilder.append(COMMA);
                                }
                            }
                            fieldValues.add(stringBuilder.toString());
                            break;
                        case FLOAT_ARRAY:
                            Float[] floatArray = (Float[]) field.get(pojo);
                            if (floatArray == null) {
                                fieldValues.add(EMPTY_STRING);
                                break;
                            }
                            for (int i = 0; i < floatArray.length; i++) {
                                stringBuilder.append(floatArray[i]);
                                if (i < floatArray.length - 1) {
                                    stringBuilder.append(COMMA);
                                }
                            }
                            fieldValues.add(stringBuilder.toString());
                            break;
                        case DOUBLE_ARRAY:
                            Double[] doubleArray = (Double[]) field.get(pojo);
                            if (doubleArray == null) {
                                fieldValues.add(EMPTY_STRING);
                                break;
                            }
                            for (int i = 0; i < doubleArray.length; i++) {
                                stringBuilder.append(doubleArray[i]);
                                if (i < doubleArray.length - 1) {
                                    stringBuilder.append(COMMA);
                                }
                            }
                            fieldValues.add(stringBuilder.toString());
                            break;
                        case LONG_ARRAY:
                            Long[] longArray = (Long[]) field.get(pojo);
                            if (longArray == null) {
                                fieldValues.add(EMPTY_STRING);
                                break;
                            }
                            for (int i = 0; i < longArray.length; i++) {
                                stringBuilder.append(longArray[i]);
                                if (i < longArray.length - 1) {
                                    stringBuilder.append(COMMA);
                                }
                            }
                            fieldValues.add(stringBuilder.toString());
                            break;
                        case CHARACTER_ARRAY:
                            Character[] characterArray = (Character[]) field.get(pojo);
                            if (characterArray == null) {
                                fieldValues.add(EMPTY_STRING);
                                break;
                            }
                            for (int i = 0; i < characterArray.length; i++) {
                                stringBuilder.append(characterArray[i]);
                                if (i < characterArray.length - 1) {
                                    stringBuilder.append(COMMA);
                                }
                            }
                            fieldValues.add(stringBuilder.toString());
                            break;
                        case CLASSTYPE:
                            fieldValues.addAll(getAnnotatedFieldValuesFromPojo(field.get(pojo), field.getType()));
                            break;
                        default:
                            Object fieldVal = field.get(pojo);
                            if (fieldVal == null) {
                                fieldValues.add(EMPTY_STRING);
                                break;
                            }
                            fieldValues.add(String.valueOf(fieldVal));
                            break;
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException | NullPointerException |
                     ExceptionInInitializerError exception) {
                throw new CSVParsingException("Exception in " + field.getName() + " : " + exception.getMessage(), exception);
            }
            index++;
        }
        return fieldValues;
    }

    /**
     * Writes the csv header elements in to the BufferedWriter and a new line
     *
     * @param clazz  Class<T>
     * @param writer Writer
     * @throws IOException If an I/O error occurs
     */
    private <T> void writeHeaderToOutputStream(Class<T> clazz, BufferedWriter writer) throws IOException {
        List<String> annotatedHeaderNameListFromClass = CSV4PojoUtils.getAnnotatedClassFieldNames(clazz);
        writer.write(getFormattedLineElements(annotatedHeaderNameListFromClass));
        writer.newLine();
    }

    /**
     * Adds extra special characters such as double quotes for MS Excel, Google Sheet or Libre Calc compatibility
     *
     * @param lineElements List<String>
     * @return String
     */
    private String getFormattedLineElements(List<String> lineElements) {
        StringBuilder formattedLine = new StringBuilder();
        for (int i = 0; i < lineElements.size(); i++) {
            String element = lineElements.get(i);
            if (element == null || element.equals(EMPTY_STRING)) {
                formattedLine.append(EMPTY_STRING);
                if (i < lineElements.size() - 1) {
                    formattedLine.append(COMMA);
                }
                continue;
            }
            if (element.contains(ONE_DOUBLE_QUOTES) || element.contains(COMMA)) {
                element = element.replace(ONE_DOUBLE_QUOTES, TWO_DOUBLE_QUOTES);
                element = wrapToFormatElement(element);
            }
            formattedLine.append(element);
            if (i < lineElements.size() - 1) {
                formattedLine.append(COMMA);
            }
        }
        return formattedLine.toString();
    }

    /**
     * Appends and prepends double quotes characters
     *
     * @param element String
     * @return String
     */
    private String wrapToFormatElement(String element) {
        return ONE_DOUBLE_QUOTES + element + ONE_DOUBLE_QUOTES;
    }
}

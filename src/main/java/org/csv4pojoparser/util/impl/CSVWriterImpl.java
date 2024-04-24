package org.csv4pojoparser.util.impl;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.exception.InputOutputStreamException;
import org.csv4pojoparser.util.CSV4PojoUtils;
import org.csv4pojoparser.util.CSVWriter;
import org.csv4pojoparser.util.CommonConstants;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kazi Tanvir Azad
 */
public class CSVWriterImpl implements CSVWriter, CommonConstants {
    @Override
    public <T> void createCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList, OutputStream outputStream) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            // Writing the header elements to the OutputStream
            writeHeaderToOutputStream(clazz, writer);
            // Reading each object from List and execute logic to create line elements and write in to the BufferedWriter
            pojoList.forEach(pojo -> {

            });

        } catch (IOException exception) {
            throw new InputOutputStreamException("OutputStream is invalid or null: ", exception);
        }

    }

    /**
     * Creates an empty csv outputStream with all the headings mapped with the given java class annotated fields
     *
     * @param clazz        Class<T>
     * @param outputStream OutputStream
     */
    @Override
    public <T> void createEmptyCSVOutputStreamFromClass(Class<T> clazz, OutputStream outputStream) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            // Writing the header elements to the OutputStream
            writeHeaderToOutputStream(clazz, writer);
        } catch (IOException exception) {
            throw new InputOutputStreamException("OutputStream is invalid or null: ", exception);
        }
    }

    private <T> List<String> getAnnotatedFieldValuesFromPojo(T pojo) {
        Class<?> clazz = pojo.getClass();
        List<String> fieldvalues = new ArrayList<>();
        List<String> annotatedClassFields = CSV4PojoUtils.getAnnotatedClassFieldNames(clazz);
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

    /**
     * Writes the csv header elements in to the BufferedWriter and a new line
     *
     * @param clazz  Class<T>
     * @param writer Writer
     * @throws IOException If an I/O error occurs
     */
    private <T> void writeHeaderToOutputStream(Class<T> clazz, Writer writer) throws IOException {
        List<String> annotatedHeaderNameListFromClass = CSV4PojoUtils.getAnnotatedClassFieldNames(clazz);
        writer.write(getFormattedLineElements(annotatedHeaderNameListFromClass));
        writer.write(System.lineSeparator());
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

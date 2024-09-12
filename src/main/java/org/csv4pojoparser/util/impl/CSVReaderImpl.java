package org.csv4pojoparser.util.impl;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.exception.CSVParsingException;
import org.csv4pojoparser.exception.FieldNotMatchedException;
import org.csv4pojoparser.exception.InputOutputStreamException;
import org.csv4pojoparser.exception.MisConfiguredClassFieldException;
import org.csv4pojoparser.util.CSV4PojoUtils;
import org.csv4pojoparser.util.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.csv4pojoparser.util.CSV4PojoUtils.charBufferSize;
import static org.csv4pojoparser.util.CommonConstants.EMPTY_STRING;
import static org.csv4pojoparser.util.CommonConstants.ONE_DOUBLE_QUOTES;
import static org.csv4pojoparser.util.CommonConstants.SPLIT_REGEX;
import static org.csv4pojoparser.util.CommonConstants.TWO_DOUBLE_QUOTES;
import static org.csv4pojoparser.util.CommonConstants.UTF8_BOM;

/**
 * @author Kazi Tanvir Azad
 */
public class CSVReaderImpl implements CSVReader {

    /**
     * Creates and returns Stream of Java objects mapped with {@link FieldType} annotation from CSV InputStream
     *
     * @param clazz       {@link Class<T>}
     * @param inputStream {@link InputStream}
     * @return Stream<T> {@link Stream<T>}
     */
    @Override
    public <T> Stream<T> createPojoStreamFromCSVInputStream(Class<T> clazz, InputStream inputStream) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), charBufferSize());
            // Getting the class field names with @FieldType annotation
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
            if (!validateHeadersWithMappedFields(annotatedClassFields, headerElements)) {
                throw new FieldNotMatchedException("CSV header elements does not match with Class fields");
            }

            // Iterator to iterate each line after header and execute logic to create and return pojo
            Iterator<T> iterator = new Iterator<T>() {
                String line;

                @Override
                public boolean hasNext() {
                    try {
                        if (line != null) {
                            return true;
                        } else {
                            line = reader.readLine();
                            if (line == null) {
                                reader.close();
                                return false;
                            }
                            return true;
                        }
                    } catch (IOException exception) {
                        throw new InputOutputStreamException("InputStream is invalid or null: ", exception);
                    }
                }

                @Override
                public T next() {
                    if (line != null || hasNext()) {
                        // Split the line by comma, but ignore commas inside double quotes
                        String[] lineElements = line.split(SPLIT_REGEX, -1);

                        line = null;

                        // Removes extra special characters such as double quotes added by MS Excel or Google Sheet
                        cleanCsvLineElements(lineElements);

                        // Creating and returning the object of the specified class with the field values passed as a List of String
                        return createPojoFromCSVLineElements(clazz, Arrays.asList(lineElements));
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            };

            // Returning the resultant Stream
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                            iterator, Spliterator.ORDERED | Spliterator.NONNULL), false)
                    .filter(Objects::nonNull);
        } catch (IOException exception) {
            throw new InputOutputStreamException("InputStream is invalid or null: ", exception);
        }
    }

    /**
     * Creates and returns Java object mapped with {@link FieldType} from List of field values (lineElements)
     *
     * @param clazz        {@link Class<T>}
     * @param lineElements {@link List<String>}
     * @return {@link T}
     */
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
                FieldType fieldType = field.getAnnotation(FieldType.class);
                if (fieldType != null) {
                    switch (fieldType.dataType()) {
                        case INTEGER:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            field.set(classInstance, Integer.valueOf(lineElement));
                            break;
                        case INTEGER_ARRAY:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            String[] rawIntegerValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Integer> integerValList = new ArrayList<>();
                            for (String s : rawIntegerValList) {
                                integerValList.add(Integer.valueOf(s));
                            }
                            field.set(classInstance,
                                    integerValList.toArray((Integer[]) Array.newInstance(field.getType().getComponentType(), integerValList.size())));
                            break;
                        case CHARACTER:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            Character charVal = lineElement.length() == 1 ? lineElement.charAt(0)
                                    : null;
                            field.set(classInstance, charVal);
                            break;
                        case CHARACTER_ARRAY:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            String[] rawCharacterValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Character> characterValList = new ArrayList<>();
                            for (String s : rawCharacterValList) {
                                characterValList.add(s.charAt(0));
                            }
                            field.set(classInstance,
                                    characterValList.toArray((Character[]) Array.newInstance(field.getType().getComponentType(), characterValList.size())));
                            break;
                        case BOOLEAN:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            field.set(classInstance, Boolean.valueOf(lineElement));
                            break;
                        case BOOLEAN_ARRAY:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            String[] rawBooleanValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Boolean> booleanValList = new ArrayList<>();
                            for (String s : rawBooleanValList) {
                                booleanValList.add(Boolean.valueOf(s));
                            }
                            field.set(classInstance,
                                    booleanValList.toArray((Boolean[]) Array.newInstance(field.getType().getComponentType(), booleanValList.size())));
                            break;
                        case FLOAT:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            field.set(classInstance, Float.valueOf(lineElement));
                            break;
                        case FLOAT_ARRAY:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            String[] rawFloatValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Float> floatValList = new ArrayList<>();
                            for (String s : rawFloatValList) {
                                floatValList.add(Float.valueOf(s));
                            }
                            field.set(classInstance,
                                    floatValList.toArray((Float[]) Array.newInstance(field.getType().getComponentType(), floatValList.size())));
                            break;
                        case LONG:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            field.set(classInstance, Long.valueOf(lineElement));
                            break;
                        case LONG_ARRAY:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            String[] rawLongValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Long> longValList = new ArrayList<>();
                            for (String s : rawLongValList) {
                                longValList.add(Long.valueOf(s));
                            }
                            field.set(classInstance,
                                    longValList.toArray((Long[]) Array.newInstance(field.getType().getComponentType(), longValList.size())));
                            break;
                        case DOUBLE:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            field.set(classInstance, Double.valueOf(lineElement));
                            break;
                        case DOUBLE_ARRAY:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            String[] rawDoubleValList = lineElement.split(SPLIT_REGEX, -1);
                            List<Double> doubleValList = new ArrayList<>();
                            for (String s : rawDoubleValList) {
                                doubleValList.add(Double.valueOf(s));
                            }
                            field.set(classInstance,
                                    doubleValList.toArray((Double[]) Array.newInstance(field.getType().getComponentType(), doubleValList.size())));
                            break;
                        case STRING:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            field.set(classInstance, lineElement);
                            break;
                        case STRING_ARRAY:
                            if (lineElement == null || lineElement.equals(EMPTY_STRING)) {
                                field.set(classInstance, null);
                                break;
                            }
                            List<String> stringValList = new ArrayList<String>() {
                                private static final long serialVersionUID = 6146247278157261184L;

                                {
                                    for (String rawStringData : lineElement.split(SPLIT_REGEX, -1)) {
                                        add(rawStringData.trim());
                                    }
                                }
                            };
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
                            throw new MisConfiguredClassFieldException("Type attribute of @FieldType annotation do not match with actual Datatype for " + field.getName());
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException | NullPointerException |
                     ExceptionInInitializerError exception) {
                throw new CSVParsingException("Exception in " + field.getName() + " : " + exception.getMessage(), exception);
            }
            index++;
            lineIndex++;
        }
        return classInstance;
    }

    /**
     * Removes extra special characters such as double quotes added by MS Excel or Google Sheet
     *
     * @param elements {@link String[]}
     */
    private void cleanCsvLineElements(String[] elements) {
        for (int i = 0; i < elements.length; i++) {

            if (elements[i] == null) {
                elements[i] = EMPTY_STRING;
                continue;
            }

            // Skipping the cleaning process for element with empty string
            if (elements[i].equals(EMPTY_STRING)) {
                continue;
            }

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
            elements[i] = element.trim();
        }
    }

    /**
     * Validates class fields with an array of csv header elements. Returns true if
     * length and all values matches else returns false
     *
     * @param csv4PojoAnnotatedClassFields {@link List<String>}
     * @param csvHeaderElements            {@link String[]}
     * @return boolean
     */
    private boolean validateHeadersWithMappedFields(List<String> csv4PojoAnnotatedClassFields, String[] csvHeaderElements) {
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
     * Remove BOM (BYTE-ORDER MARK) if it's present in the line. For UTF-8 the BOM is: 0xEF, 0xBB, 0xBF
     *
     * @param line {@link String}
     * @return {@link String}
     */
    private String removeUTF8BOM(String line) {
        if (line.startsWith(UTF8_BOM)) {
            line = line.substring(1);
        }
        return line;
    }
}

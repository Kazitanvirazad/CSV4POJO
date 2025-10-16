package io.github.csv4pojo.utils;

import io.github.csv4pojo.annotation.FieldType;
import io.github.csv4pojo.annotation.FieldType.Type;
import io.github.csv4pojo.exception.MisConfiguredClassFieldException;
import io.github.csv4pojo.exception.ReflectiveException;
import io.github.csv4pojo.model.CsvClassConfiguration;
import io.github.csv4pojo.model.CsvClassField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static io.github.csv4pojo.common.CommonConstants.EMPTY_STRING;
import static io.github.csv4pojo.common.CommonConstants.FIELD_PATH_REGEX;
import static io.github.csv4pojo.common.CommonConstants.PIPE;

/**
 * @author Kazi Tanvir Azad
 */
public final class CSV4PojoUtils {

    private CSV4PojoUtils() {
        throw new IllegalArgumentException("Object creation of this class is not allowed");
    }

    public static <T> CsvClassConfiguration getCsvClassConfiguration(final Class<T> clazz) {
        final Map<String, CsvClassField> csvClassFields = getCsvClassFieldMap(clazz, null);
        final List<String> csvHeaders = csvClassFields
                .entrySet()
                .stream()
                .map(entry -> entry.getValue().getCsvFieldName())
                .collect(Collectors.toList());
        return new CsvClassConfiguration(csvClassFields, csvHeaders, clazz);
    }

    public static <T> Map<String, CsvClassField> getCsvClassFieldMap(final Class<T> clazz, final String path) {
        final Map<String, CsvClassField> csvClassFieldMap = new LinkedHashMap<>();
        Arrays.stream(clazz.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> field.isAnnotationPresent(FieldType.class))
                .forEach(field -> {
                    if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
                        csvClassFieldMap.putAll(getCsvClassFieldMap(field.getType(),
                                null == path ? field.getName() : path + PIPE + field.getName()));
                    } else {
                        String csvFieldName = field.getDeclaredAnnotation(FieldType.class).csvColumnName().trim();
                        CsvClassField classField = new CsvClassField(field, csvFieldName);
                        if (null != path) {
                            classField.setFieldPath(path + PIPE + classField.getCsvField().getName());
                        }
                        csvClassFieldMap.put(csvFieldName, classField);
                    }
                });
        return csvClassFieldMap;
    }

    public static <T, C> String getDeclaredFieldValue(final String csvFieldPath, T pojo) throws ReflectiveException {
        try {
            String[] fieldPaths = csvFieldPath.split(FIELD_PATH_REGEX);
            C object = (C) pojo;
            Field field;
            for (String path : fieldPaths) {
                field = object.getClass().getDeclaredField(path);
                field.setAccessible(true);
                object = (C) field.get(object);
            }
            if (null != object)
                return (String) object;
        } catch (Exception exception) {
            throw new ReflectiveException("Reflection operation error", exception);
        }
        return EMPTY_STRING;
    }

    public static <T> String getDeclaredFieldValue(Field csvField, T pojo) throws ReflectiveException {
        try {
            Object fieldValue = csvField.get(pojo);
            if (null != fieldValue) {
                return (String) fieldValue;
            }
        } catch (Exception exception) {
            throw new ReflectiveException("Reflection operation error", exception);
        }
        return EMPTY_STRING;
    }

    /**
     * Return count of the fields of the class annotated with {@link FieldType} annotation
     * and include all the annotated fields of composition class  with {@link FieldType} nested class
     *
     * @param clazz {@code Class<?>}
     * @return count of {@link FieldType} annotated fields
     */
    public static int getAnnotatedFieldCount(Class<?> clazz) {
        AtomicInteger count = new AtomicInteger();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(FieldType.class))
                .forEach(field -> {
                    if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
                        count.addAndGet(getAnnotatedFieldCount(field.getType()));
                    } else {
                        count.incrementAndGet();
                    }
                });
        return count.get();
    }

    /**
     * Returns list of Field which are annotated with {@link FieldType} annotation
     *
     * @param <T>   the class of the value
     * @param clazz {@link Class}
     * @return {@code List<Field>}
     */
    public static <T> List<Field> getAnnotatedClassFieldList(Class<T> clazz) {
        final List<Field> fields = new ArrayList<>();
        try {
            Arrays.stream(clazz.getDeclaredFields())
                    .peek(field -> field.setAccessible(true))
                    .filter(field -> field.isAnnotationPresent(FieldType.class))
                    .forEach(fields::add);
        } catch (RuntimeException exception) {
            throw new MisConfiguredClassFieldException("CSV4Pojo FieldType Annotations not properly set: ", exception);
        }
        return fields;
    }

    /**
     * Returns list of class field names which are annotated with {@link FieldType} annotation
     *
     * @param clazz {@link Class<T>}
     * @return {@link List<String>}
     */
    public static <T> List<String> getAnnotatedClassFieldNames(Class<T> clazz) {
        final List<String> fieldNames = new ArrayList<>();
        try {
            Arrays.stream(clazz.getDeclaredFields())
                    .peek(field -> field.setAccessible(true))
                    .filter(field -> field.isAnnotationPresent(FieldType.class))
                    .forEach(field -> {
                        if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
                            fieldNames.addAll(getAnnotatedClassFieldNames(field.getType()));
                        } else {
                            String fieldName = field.getDeclaredAnnotation(FieldType.class).csvColumnName();
                            fieldNames.add(fieldName);
                        }
                    });
        } catch (RuntimeException exception) {
            throw new MisConfiguredClassFieldException("CSV4Pojo FieldType Annotations are incorrectly set: ", exception);
        }
        return fieldNames;
    }

    /**
     * Returns buffer size to be used by {@link java.io.BufferedWriter} and {@link java.io.BufferedReader}.
     * First priority goes to environment variable CHAR_BUFFER_SIZE, if this fails then it defaults to
     * fallback size i.e. 8192
     *
     * @return buffer size
     */
    public static int charBufferSize() {
        String charBufferSize = System.getenv("CHAR_BUFFER_SIZE");
        int fallbackCharBufferSize = 8192;
        if (charBufferSize != null) {
            try {
                fallbackCharBufferSize = Integer.parseInt(charBufferSize);
            } catch (NumberFormatException ignored) {
            }
        }
        return fallbackCharBufferSize;
    }
}

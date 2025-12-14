package io.github.csv4pojo.utils;

import io.github.csv4pojo.annotation.FieldType;
import io.github.csv4pojo.annotation.FieldType.Type;
import io.github.csv4pojo.exception.MisConfiguredClassFieldException;
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

import static io.github.csv4pojo.common.CommonConstants.PIPE;
import static io.github.csv4pojo.helper.CsvWriterValidationHelper.isValidFieldType;

/**
 * @author Kazi Tanvir Azad
 */
public final class CSV4PojoUtils {

    private CSV4PojoUtils() {
        throw new AssertionError("Object creation of this class is not allowed");
    }

    public static CsvClassConfiguration<?> getCsvClassConfiguration(final Class<?> clazz) {
        final Map<String, CsvClassField> csvClassFields = getCsvClassFieldMap(clazz, null);
        final List<String> csvHeaders = csvClassFields
                .values()
                .stream()
                .map(CsvClassField::getCsvFieldName)
                .collect(Collectors.toList());
        return new CsvClassConfiguration<>(csvClassFields, csvHeaders, clazz);
    }

    public static Map<String, CsvClassField> getCsvClassFieldMap(final Class<?> clazz, final String path) {
        final Map<String, CsvClassField> csvClassFieldMap = new LinkedHashMap<>();
        Arrays.stream(clazz.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> field.isAnnotationPresent(FieldType.class))
                .forEach(field -> {
                    Type type = field.getDeclaredAnnotation(FieldType.class).dataType();
                    if (Type.CLASSTYPE == type) {
                        Map<String, CsvClassField> compositeCsvClassFieldMap = getCsvClassFieldMap(field.getType(),
                                null == path ? field.getName() : path + PIPE + field.getName());
                        compositeCsvClassFieldMap.keySet().forEach(key -> {
                            if (csvClassFieldMap.containsKey(key)) {
                                throw new MisConfiguredClassFieldException("FieldType annotation's attribute csvColumnName should" +
                                        " not have duplicate value for field: '" + field.getName() + "', column: '" + key + "'." +
                                        " CSV column names must be unique.");
                            }
                        });
                        csvClassFieldMap.putAll(compositeCsvClassFieldMap);
                    } else {
                        if (!isValidFieldType(field, type)) {
                            throw new MisConfiguredClassFieldException("FieldType annotation's dataType attribute mapping " +
                                    "doesn't match with the actual type of the field: '" + field.getName() + "'");
                        }
                        String csvFieldName = field.getDeclaredAnnotation(FieldType.class).csvColumnName().trim();
                        if (csvFieldName.isEmpty()) {
                            throw new MisConfiguredClassFieldException("FieldType annotation's attribute csvColumnName must" +
                                    " have non whitespace value for field: '" + field.getName() + "'");
                        }
                        CsvClassField classField = new CsvClassField(field, type, csvFieldName);
                        classField.setFieldPath(null == path ? classField.getCsvField().getName()
                                : path + PIPE + classField.getCsvField().getName());
                        if (csvClassFieldMap.containsKey(csvFieldName)) {
                            throw new MisConfiguredClassFieldException("FieldType annotation's attribute csvColumnName should" +
                                    " not have duplicate value for field: '" + field.getName() + "', column: '" + csvFieldName + "'." +
                                    " CSV column names must be unique.");
                        }
                        csvClassFieldMap.put(csvFieldName, classField);
                    }
                });
        return csvClassFieldMap;
    }

    public static FieldReader getFieldReader(final CsvClassField csvClassField) {
        Type type = csvClassField.getType();
        if (Type.INTEGER_ARRAY == type ||
                Type.STRING_ARRAY == type ||
                Type.BOOLEAN_ARRAY == type ||
                Type.FLOAT_ARRAY == type ||
                Type.DOUBLE_ARRAY == type ||
                Type.LONG_ARRAY == type ||
                Type.CHARACTER_ARRAY == type) {
            return new ArrayFieldReader();
        }
        return new DefaultFieldReader();
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

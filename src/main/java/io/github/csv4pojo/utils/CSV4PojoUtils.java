package io.github.csv4pojo.utils;

import io.github.csv4pojo.annotation.FieldType;
import io.github.csv4pojo.annotation.Type;
import io.github.csv4pojo.exception.MisConfiguredClassFieldException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kazi Tanvir Azad
 */
public final class CSV4PojoUtils {

    private CSV4PojoUtils() {
    }

    /**
     * Return count of the fields of the class annotated with {@link FieldType} annotation
     * and include all the annotated fields of composition class  with {@link FieldType} nested class
     *
     * @param clazz {@link Class<?>}
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
     * @param clazz {@link Class<T>}
     * @return {@link List<Field>}
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

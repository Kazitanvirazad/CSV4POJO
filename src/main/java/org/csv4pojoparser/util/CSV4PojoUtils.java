package org.csv4pojoparser.util;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;
import org.csv4pojoparser.exception.MisConfiguredClassFieldException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kazi Tanvir Azad
 */
public class CSV4PojoUtils implements CommonConstants {

    /**
     * Return count of the fields annotated with {@link FieldType}
     *
     * @param clazz Class<?>
     * @return count of {@link FieldType} annotated fields
     */
    public static int getAnnotatedFieldCount(Class<?> clazz) {
        int count = 0;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(FieldType.class)) {
                if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
                    count += getAnnotatedFieldCount(field.getType());
                } else {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Returns the first argument if it is non-null and otherwise returns the non-null second argument.
     *
     * @param obj        an object
     * @param defaultObj a non-null object to return if the first argument is null
     * @return the first argument if it is non-null and otherwise the second argument if it is non-null
     * @throws NullPointerException if both obj is null and defaultObj is null
     * @since 9
     * This method has been copied from java.util.Objects::requireNonNull from OpenJDK 11
     */
    public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : requireNonNull(defaultObj, "defaultObj");
    }

    /**
     * Checks that the specified object reference is not null. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors.
     *
     * @param obj the object reference to check for nullity
     * @param <T> the type of the reference
     * @return obj if not null
     * @throws NullPointerException if obj is null
     * @since 9
     * This method has been copied from java.util.Objects::requireNonNull from OpenJDK 11
     */
    private static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

    /**
     * Returns list of Field which are annotated with {@link FieldType} annotation
     *
     * @param clazz Class<T>
     * @return List<Field>
     */
    public static <T> List<Field> getAnnotatedClassFieldList(Class<T> clazz) {
        List<Field> fields = new ArrayList<>();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(FieldType.class)) {
                    fields.add(field);
                }
            }
        } catch (RuntimeException exception) {
            throw new MisConfiguredClassFieldException("CSV4Pojo FieldType Annotations not properly set: ", exception);
        }
        return fields;
    }

    /**
     * Returns list of class field names which are annotated with {@link FieldType} annotation
     *
     * @param clazz Class<T>
     * @return List<String>
     */
    public static <T> List<String> getAnnotatedClassFieldNames(Class<T> clazz) {
        List<String> fieldNames = new ArrayList<>();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(FieldType.class)) {
                    if (field.getDeclaredAnnotation(FieldType.class).dataType() == Type.CLASSTYPE) {
                        fieldNames.addAll(getAnnotatedClassFieldNames(field.getType()));
                    } else {
                        String fieldName = getAnnotatedFieldName(field);
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
     * Returns field name from csvColumnName attribute value if exists in {@link FieldType} annotation,
     * else returns original field name
     *
     * @param field {@link Field}
     * @return String
     */
    public static String getAnnotatedFieldName(Field field) {
        return !field.getDeclaredAnnotation(FieldType.class).csvColumnName().isEmpty() ?
                field.getDeclaredAnnotation(FieldType.class).csvColumnName() : field.getName();
    }
}

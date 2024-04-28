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
     * Return count of the fields annotated with {@link FieldType} of
     * the parent class, and it's annotated with {@link FieldType} nested class
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

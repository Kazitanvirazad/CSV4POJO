package io.github.csv4pojo.helper;

import io.github.csv4pojo.annotation.FieldType.Type;
import io.github.csv4pojo.exception.CsvParsingException;
import io.github.csv4pojo.exception.MisConfiguredClassException;
import io.github.csv4pojo.exception.StreamException;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Kazi Tanvir Azad
 */
public final class CsvWriterValidationHelper {
    private CsvWriterValidationHelper() {
        throw new AssertionError("Object creation of this class is not allowed");
    }

    public static boolean isValidFieldType(Field field, Type type) {
        java.lang.reflect.Type fieldType = field.getType();
        if (field.getType().isArray()) {
            if (fieldType == Integer[].class || fieldType == int[].class) {
                return type.equals(Type.INTEGER_ARRAY);
            }
            if (fieldType == String[].class) {
                return type.equals(Type.STRING_ARRAY);
            }
            if (fieldType == Boolean[].class || fieldType == boolean[].class) {
                return type.equals(Type.BOOLEAN_ARRAY);
            }
            if (fieldType == Float[].class || fieldType == float[].class) {
                return type.equals(Type.FLOAT_ARRAY);
            }
            if (fieldType == Double[].class || fieldType == double[].class) {
                return type.equals(Type.DOUBLE_ARRAY);
            }
            if (fieldType == Long[].class || fieldType == long[].class) {
                return type.equals(Type.LONG_ARRAY);
            }
            if (fieldType == Character[].class || fieldType == char[].class) {
                return type.equals(Type.CHARACTER_ARRAY);
            }
        } else {
            if (fieldType == Integer.class || fieldType == int.class) {
                return type.equals(Type.INTEGER);
            }
            if (fieldType == String.class) {
                return type.equals(Type.STRING);
            }
            if (fieldType == Boolean.class || fieldType == boolean.class) {
                return type.equals(Type.BOOLEAN);
            }
            if (fieldType == Float.class || fieldType == float.class) {
                return type.equals(Type.FLOAT);
            }
            if (fieldType == Double.class || fieldType == double.class) {
                return type.equals(Type.DOUBLE);
            }
            if (fieldType == Long.class || fieldType == long.class) {
                return type.equals(Type.LONG);
            }
            if (fieldType == Character.class || fieldType == char.class) {
                return type.equals(Type.CHARACTER);
            }
        }
        return false;
    }

    public static void validateCSVOutputStream(Class<?> clazz, Stream<?> pojoStream, OutputStream outputStream) {
        validateOutputStream(outputStream);
        validateClass(clazz);
        validatePojoStream(pojoStream);
    }

    public static void validateCSVOutputStream(Class<?> clazz, List<?> pojoList, OutputStream outputStream) {
        validateOutputStream(outputStream);
        validateClass(clazz);
        validatePojoList(pojoList);
    }

    public static void validateCSVOutputStream(Class<?> clazz, OutputStream outputStream) {
        validateOutputStream(outputStream);
        validateClass(clazz);
    }

    private static void validatePojoList(List<?> pojoList) {
        if (null == pojoList) {
            throw new CsvParsingException("Pojo list argument is invalid or null");
        }
    }

    private static void validatePojoStream(Stream<?> pojoStream) {
        if (null == pojoStream) {
            throw new CsvParsingException("Pojo stream argument is invalid or null");
        }
    }

    private static void validateOutputStream(OutputStream outputStream) {
        if (null == outputStream) {
            throw new StreamException("OutputStream argument is invalid or null");
        }
    }

    private static void validateClass(Class<?> clazz) {
        if (null == clazz) {
            throw new MisConfiguredClassException("Class argument is invalid or null");
        }
    }
}

package io.github.csv4pojo.utils;

import io.github.csv4pojo.model.CsvClassField;

import java.lang.reflect.Field;

import static io.github.csv4pojo.common.CommonConstants.FIELD_PATH_REGEX;

/**
 * @author Kazi Tanvir Azad
 */
public interface FieldReader {
    <T> String read(CsvClassField csvClassField, T pojo);

    default <T> Object readValue(CsvClassField csvClassField, T pojo) {
        String fieldPath = csvClassField.getFieldPath();
        Field field;
        try {
            String[] fieldPaths = fieldPath.split(FIELD_PATH_REGEX);
            Object object = pojo;
            for (String path : fieldPaths) {
                field = object.getClass().getDeclaredField(path);
                field.setAccessible(true);
                object = field.get(object);
            }
            return object;
        } catch (Exception exception) {
            return null;
        }
    }
}

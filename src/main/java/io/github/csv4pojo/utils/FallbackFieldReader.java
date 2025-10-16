package io.github.csv4pojo.utils;

import io.github.csv4pojo.model.CsvClassField;

/**
 * @author Kazi Tanvir Azad
 */
public class FallbackFieldReader<T> implements FieldReader<T> {
    @Override
    public String read(CsvClassField csvClassField, T pojo) {
        return "";
    }
}

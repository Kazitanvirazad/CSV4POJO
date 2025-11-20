package io.github.csv4pojo.utils;

import io.github.csv4pojo.model.CsvClassField;

/**
 * @author Kazi Tanvir Azad
 */
public interface FieldReader {
    <T> String read(CsvClassField csvClassField, T pojo);
}

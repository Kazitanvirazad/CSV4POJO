package io.github.csv4pojo.utils;

import io.github.csv4pojo.model.CsvClassField;

import java.util.Objects;

import static io.github.csv4pojo.common.CommonConstants.EMPTY_STRING;

/**
 * @author Kazi Tanvir Azad
 */
public class DefaultFieldReader implements FieldReader {

    @Override
    public <T> String read(CsvClassField csvClassField, T pojo) {
        Object value = readValue(csvClassField, pojo);
        if (Objects.nonNull(value)) {
            return String.valueOf(value);
        }
        return EMPTY_STRING;
    }
}

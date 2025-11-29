package io.github.csv4pojo.utils;

import io.github.csv4pojo.model.CsvClassField;

import java.lang.reflect.Array;
import java.util.Objects;

import static io.github.csv4pojo.common.CommonConstants.COMMA;
import static io.github.csv4pojo.common.CommonConstants.EMPTY_STRING;

/**
 * @author Kazi Tanvir Azad
 */
public class ArrayFieldReader implements FieldReader {

    @Override
    public <T> String read(CsvClassField csvClassField, T pojo) {
        Object valueArray = readValue(csvClassField, pojo);
        if (Objects.nonNull(valueArray) && valueArray.getClass().isArray()) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                int length = Array.getLength(valueArray);
                for (int i = 0; i < length; i++) {
                    stringBuilder.append(Array.get(valueArray, i));
                    if (i < length - 1) {
                        stringBuilder.append(COMMA);
                    }
                }
                return stringBuilder.toString();
            } catch (Exception exception) {
                return EMPTY_STRING;
            }
        }
        return EMPTY_STRING;
    }
}

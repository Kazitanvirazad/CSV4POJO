package io.github.csv4pojo.utils;

import io.github.csv4pojo.model.CsvClassField;

import java.lang.reflect.Field;

import static io.github.csv4pojo.common.CommonConstants.COMMA;
import static io.github.csv4pojo.common.CommonConstants.EMPTY_STRING;

/**
 * @author Kazi Tanvir Azad
 */
public class ArrayFieldReader implements FieldReader {

    @Override
    public <T> String read(CsvClassField csvClassField, T pojo) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Field field = csvClassField.getCsvField();
            field.setAccessible(true);
            Object[] valueArray = (Object[]) field.get(pojo);
            if (valueArray == null) {
                return EMPTY_STRING;
            }
            for (int i = 0; i < valueArray.length; i++) {
                stringBuilder.append(valueArray[i]);
                if (i < valueArray.length - 1) {
                    stringBuilder.append(COMMA);
                }
            }
            return stringBuilder.toString();
        } catch (Exception exception) {
            return EMPTY_STRING;
        }
    }
}

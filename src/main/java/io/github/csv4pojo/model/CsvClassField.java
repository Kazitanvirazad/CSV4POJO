package io.github.csv4pojo.model;

import java.lang.reflect.Field;

public class CsvClassField {
    private final Field csvField;
    private final String csvFieldName;

    public Field getCsvField() {
        return csvField;
    }

    public String getCsvFieldName() {
        return csvFieldName;
    }

    public CsvClassField(Field csvField, String csvFieldName) {
        this.csvField = csvField;
        this.csvFieldName = csvFieldName;
    }
}

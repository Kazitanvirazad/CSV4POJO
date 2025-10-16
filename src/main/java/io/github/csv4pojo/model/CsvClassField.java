package io.github.csv4pojo.model;

import java.lang.reflect.Field;

/**
 * @author Kazi Tanvir Azad
 */
public class CsvClassField {
    private Field csvField;
    private String csvFieldName;
    private String fieldPath;

    public CsvClassField(Field csvField, String csvFieldName, String fieldPath) {
        this.csvField = csvField;
        this.csvFieldName = csvFieldName;
        this.fieldPath = fieldPath;
    }

    public CsvClassField(Field csvField, String csvFieldName) {
        this(csvField, csvFieldName, null);
    }

    public Field getCsvField() {
        return csvField;
    }

    public void setCsvField(Field csvField) {
        this.csvField = csvField;
    }

    public String getCsvFieldName() {
        return csvFieldName;
    }

    public void setCsvFieldName(String csvFieldName) {
        this.csvFieldName = csvFieldName;
    }

    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath;
    }
}

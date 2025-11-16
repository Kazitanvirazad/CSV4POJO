package io.github.csv4pojo.model;

import io.github.csv4pojo.annotation.FieldType.Type;

import java.lang.reflect.Field;

/**
 * @author Kazi Tanvir Azad
 */
public class CsvClassField {
    private Field csvField;
    private String csvFieldName;
    private String fieldPath;
    private Type type;

    public CsvClassField(Field csvField, String csvFieldName, Type type, String fieldPath) {
        this.csvField = csvField;
        this.csvFieldName = csvFieldName;
        this.fieldPath = fieldPath;
        this.type = type;
    }

    public CsvClassField(Field csvField, Type type, String csvFieldName) {
        this(csvField, csvFieldName, type, null);
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

import java.util.Arrays;

public class EntityChild {
    @FieldType(dataType = Type.STRING_ARRAY)
    private String[] strArray;
    @FieldType(dataType = Type.INTEGER_ARRAY)
    private Integer[] intArray;
    @FieldType(dataType = Type.LONG_ARRAY)
    private Long[] longArray;
    @FieldType(dataType = Type.FLOAT_ARRAY)
    private Float[] floatArray;
    @FieldType(dataType = Type.DOUBLE_ARRAY)
    private Double[] doubleArray;
    @FieldType(dataType = Type.CHARACTER_ARRAY)
    private Character[] characterArray;
    @FieldType(dataType = Type.BOOLEAN_ARRAY)
    private Boolean[] booleanArray;

    public String[] getStrArray() {
        return strArray;
    }

    public void setStrArray(String[] strArray) {
        this.strArray = strArray;
    }

    public Integer[] getIntArray() {
        return intArray;
    }

    public void setIntArray(Integer[] intArray) {
        this.intArray = intArray;
    }

    public Long[] getLongArray() {
        return longArray;
    }

    public void setLongArray(Long[] longArray) {
        this.longArray = longArray;
    }

    public Float[] getFloatArray() {
        return floatArray;
    }

    public void setFloatArray(Float[] floatArray) {
        this.floatArray = floatArray;
    }

    public Double[] getDoubleArray() {
        return doubleArray;
    }

    public void setDoubleArray(Double[] doubleArray) {
        this.doubleArray = doubleArray;
    }

    public Character[] getCharacterArray() {
        return characterArray;
    }

    public void setCharacterArray(Character[] characterArray) {
        this.characterArray = characterArray;
    }

    public Boolean[] getBooleanArray() {
        return booleanArray;
    }

    public void setBooleanArray(Boolean[] booleanArray) {
        this.booleanArray = booleanArray;
    }

    @Override
    public String toString() {
        return "EntityChild{" +
                "strArray=" + Arrays.toString(strArray) +
                ", intArray=" + Arrays.toString(intArray) +
                ", longArray=" + Arrays.toString(longArray) +
                ", floatArray=" + Arrays.toString(floatArray) +
                ", doubleArray=" + Arrays.toString(doubleArray) +
                ", characterArray=" + Arrays.toString(characterArray) +
                ", booleanArray=" + Arrays.toString(booleanArray) +
                '}';
    }

    public EntityChild(String[] strArray, Integer[] intArray, Long[] longArray, Float[] floatArray, Double[] doubleArray, Character[] characterArray, Boolean[] booleanArray) {
        this.strArray = strArray;
        this.intArray = intArray;
        this.longArray = longArray;
        this.floatArray = floatArray;
        this.doubleArray = doubleArray;
        this.characterArray = characterArray;
        this.booleanArray = booleanArray;
    }

    public EntityChild() {
    }
}

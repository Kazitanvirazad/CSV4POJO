package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

import java.util.Arrays;

public class Category {
    @FieldType(dataType = Type.STRING, csvColumnName = "category_name")
    private String categoryName;

    @FieldType(dataType = Type.STRING_ARRAY)
    private String[] tags;

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}

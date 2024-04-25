package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

import java.util.Arrays;

public class Category {
    @FieldType(dataType = Type.STRING, csvColumnName = "category_name")
    private String categoryName;

    @FieldType(dataType = Type.STRING_ARRAY)
    private String[] tags;

    public Category(String categoryName, String[] tags) {
        this.categoryName = categoryName;
        this.tags = tags;
    }

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}

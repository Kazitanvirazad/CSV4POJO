package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

import java.util.Arrays;
import java.util.Objects;

public class Category {
    @FieldType(dataType = Type.STRING, csvColumnName = "category_name")
    private String categoryName;

    @FieldType(dataType = Type.STRING_ARRAY)
    private String[] tags;

    @FieldType(dataType = Type.CLASSTYPE)
    private Variant variant;

    public Category(String categoryName, String[] tags, Variant variant) {
        this.categoryName = categoryName;
        this.tags = tags;
        this.variant = variant;
    }

    public Category() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryName, category.categoryName) && Objects.deepEquals(tags, category.tags) && Objects.equals(variant, category.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, Arrays.hashCode(tags), variant);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", variant=" + variant +
                '}';
    }
}

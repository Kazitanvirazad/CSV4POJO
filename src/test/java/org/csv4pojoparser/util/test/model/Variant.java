package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

import java.util.Objects;

public class Variant {
    @FieldType(dataType = Type.STRING, csvColumnName = "variant_name")
    private String variantName;
    @FieldType(dataType = Type.STRING, csvColumnName = "variant_type")
    private String variantType;

    public Variant() {
    }

    public Variant(String variantName, String variantType) {
        this.variantName = variantName;
        this.variantType = variantType;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "variantName='" + variantName + '\'' +
                ", variantType='" + variantType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variant variant = (Variant) o;
        return Objects.equals(variantName, variant.variantName) && Objects.equals(variantType, variant.variantType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variantName, variantType);
    }
}

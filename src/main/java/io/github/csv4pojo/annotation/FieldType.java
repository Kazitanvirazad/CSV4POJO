package io.github.csv4pojo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kazi Tanvir Azad
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldType {
    Type dataType();

    String csvColumnName();

    enum Type {

        INTEGER,
        STRING,
        BOOLEAN,
        FLOAT,
        DOUBLE,
        LONG,
        CHARACTER,
        CLASSTYPE,
        INTEGER_ARRAY,
        STRING_ARRAY,
        BOOLEAN_ARRAY,
        FLOAT_ARRAY,
        DOUBLE_ARRAY,
        LONG_ARRAY,
        CHARACTER_ARRAY
    }
}

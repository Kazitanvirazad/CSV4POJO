package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

public class Ram {
    @FieldType(dataType = Type.INT, csvColumnName = "memoryamount")
    private int memoryAmount;
    @FieldType(dataType = Type.INT, csvColumnName = "memoryspeed")
    private int memorySpeed;

    @Override
    public String toString() {
        return "Ram{" +
                "memoryAmount=" + memoryAmount +
                ", memorySpeed=" + memorySpeed +
                '}';
    }
}

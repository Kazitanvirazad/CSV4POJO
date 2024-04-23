package com.csv4pojo.util.test.model;

import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;

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

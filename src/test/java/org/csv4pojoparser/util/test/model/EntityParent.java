package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

public class EntityParent {
    @FieldType(dataType = Type.STRING)
    private String strParent;
    @FieldType(dataType = Type.INTEGER)
    private Integer intParent;
    @FieldType(dataType = Type.LONG)
    private Long longParent;
    @FieldType(dataType = Type.FLOAT)
    private Float floatParent;
    @FieldType(dataType = Type.DOUBLE)
    private Double doubleParent;
    @FieldType(dataType = Type.CHARACTER)
    private Character characterParent;
    @FieldType(dataType = Type.BOOLEAN)
    private Boolean booleanParent;
    @FieldType(dataType = Type.CLASSTYPE)
    private EntityChild entityChild;

    public String getStrParent() {
        return strParent;
    }

    public void setStrParent(String strParent) {
        this.strParent = strParent;
    }

    public Integer getIntParent() {
        return intParent;
    }

    public void setIntParent(Integer intParent) {
        this.intParent = intParent;
    }

    public Long getLongParent() {
        return longParent;
    }

    public void setLongParent(Long longParent) {
        this.longParent = longParent;
    }

    public Float getFloatParent() {
        return floatParent;
    }

    public void setFloatParent(Float floatParent) {
        this.floatParent = floatParent;
    }

    public Double getDoubleParent() {
        return doubleParent;
    }

    public void setDoubleParent(Double doubleParent) {
        this.doubleParent = doubleParent;
    }

    public Character getCharacterParent() {
        return characterParent;
    }

    public void setCharacterParent(Character characterParent) {
        this.characterParent = characterParent;
    }

    public Boolean getBooleanParent() {
        return booleanParent;
    }

    public void setBooleanParent(Boolean booleanParent) {
        this.booleanParent = booleanParent;
    }

    public EntityChild getEntityChild() {
        return entityChild;
    }

    public void setEntityChild(EntityChild entityChild) {
        this.entityChild = entityChild;
    }

    @Override
    public String toString() {
        return "EntityParent{" +
                "strParent='" + strParent + '\'' +
                ", intParent=" + intParent +
                ", longParent=" + longParent +
                ", floatParent=" + floatParent +
                ", doubleParent=" + doubleParent +
                ", characterParent=" + characterParent +
                ", booleanParent=" + booleanParent +
                ", entityChild=" + entityChild +
                '}';
    }

    public EntityParent(String strParent, Integer intParent, Long longParent, Float floatParent, Double doubleParent, Character characterParent, Boolean booleanParent, EntityChild entityChild) {
        this.strParent = strParent;
        this.intParent = intParent;
        this.longParent = longParent;
        this.floatParent = floatParent;
        this.doubleParent = doubleParent;
        this.characterParent = characterParent;
        this.booleanParent = booleanParent;
        this.entityChild = entityChild;
    }

    public EntityParent() {
    }
}

package org.csv4pojoparser.common;

/**
 * @author Kazi Tanvir Azad
 */
public final class CommonConstants {
    StreamException static final String SPLIT_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    public static final String ONE_DOUBLE_QUOTES = "\"";
    public static final String TWO_DOUBLE_QUOTES = "\"\"";
    public static final String COMMA = ",";
    public static final String UTF8_BOM = "\uFEFF";
    public static final String EMPTY_STRING = "";

    private CommonConstants() {}
}

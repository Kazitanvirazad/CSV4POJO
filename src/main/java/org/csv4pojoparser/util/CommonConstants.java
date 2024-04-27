package org.csv4pojoparser.util;

/**
 * @author Kazi Tanvir Azad
 */
public interface CommonConstants {
    String SPLIT_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    String ONE_DOUBLE_QUOTES = "\"";
    String TWO_DOUBLE_QUOTES = "\"\"";
    String COMMA = ",";
    String UTF8_BOM = "\uFEFF";
    String EMPTY_STRING = "";

    default int CHAR_BUFFER_SIZE() {
        String char_buffer_size = System.getenv("CHAR_BUFFER_SIZE");
        int int_char_buffer_size = 8192;
        if (char_buffer_size != null) {
            try {
                int_char_buffer_size = Integer.parseInt(char_buffer_size);
            } catch (NumberFormatException exception) {
            }
        }
        return int_char_buffer_size;
    }
}

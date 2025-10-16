package io.github.csv4pojo.model;

import io.github.csv4pojo.exception.MisConfiguredClassFieldException;

import java.util.List;
import java.util.Map;

/**
 * @author Kazi Tanvir Azad
 */
public class CsvClassConfiguration {
    private int csvFieldCount;
    private Map<String, CsvClassField> csvClassFieldMap;
    private List<String> csvHeaders;
    private Class<?> clazz;

    private CsvClassConfiguration(final Map<String, CsvClassField> csvClassFieldMap) {
        if (null != csvClassFieldMap && !csvClassFieldMap.isEmpty())
            this.csvFieldCount = csvClassFieldMap.size();
        else
            throw new MisConfiguredClassFieldException("Annotate fields with FieldType annotation for field to csv mapping");
    }

    public <T> CsvClassConfiguration(final Map<String, CsvClassField> csvClassFieldMap,
                                     final List<String> csvHeaders,
                                     final Class<T> clazz) {
        this(csvClassFieldMap);
        this.csvClassFieldMap = csvClassFieldMap;
        this.csvHeaders = csvHeaders;
        this.clazz = clazz;
    }

    public Map<String, CsvClassField> getCsvClassFieldMap() {
        return csvClassFieldMap;
    }

    public void setCsvClassFieldMap(Map<String, CsvClassField> csvClassFieldMap) {
        this.csvClassFieldMap = csvClassFieldMap;
    }

    public int getCsvFieldCount() {
        return csvFieldCount;
    }

    public void setCsvFieldCount(int csvFieldCount) {
        this.csvFieldCount = csvFieldCount;
    }

    public List<String> getCsvHeaders() {
        return csvHeaders;
    }

    public void setCsvHeaders(List<String> csvHeaders) {
        this.csvHeaders = csvHeaders;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}

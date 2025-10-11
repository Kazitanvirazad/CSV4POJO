package io.github.csv4pojo.model;

import io.github.csv4pojo.exception.MisConfiguredClassFieldException;

import java.util.List;
import java.util.Map;

public class CsvClassConfiguration {
    private int csvFieldCount;
    private Map<String, CsvClassField> csvClassFieldMap;
    private List<String> csvHeaders;

    private CsvClassConfiguration(final Map<String, CsvClassField> csvClassFieldMap) {
        if (null != csvClassFieldMap && !csvClassFieldMap.isEmpty())
            this.csvFieldCount = csvClassFieldMap.size();
        else
            throw new MisConfiguredClassFieldException("List of CsvClassField is empty or null");
    }

    public CsvClassConfiguration(final Map<String, CsvClassField> csvClassFieldMap, final List<String> csvHeaders) {
        this(csvClassFieldMap);
        this.csvClassFieldMap = csvClassFieldMap;
        this.csvHeaders = csvHeaders;
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
}

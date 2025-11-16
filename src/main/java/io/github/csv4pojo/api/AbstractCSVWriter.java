package io.github.csv4pojo.api;

import io.github.csv4pojo.model.CsvClassConfiguration;
import io.github.csv4pojo.utils.CSV4PojoUtils;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

import static io.github.csv4pojo.helper.CsvWriterValidationHelper.validateCSVOutputStream;

/**
 * @author Kazi Tanvir Azad
 */
public abstract class AbstractCSVWriter implements CSVWriter {
    protected final CsvClassConfiguration<?> csvClassConfiguration;

    protected AbstractCSVWriter(Class<?> clazz) {
        if (null == clazz)
            throw new IllegalArgumentException("Class parameter is required");
        this.csvClassConfiguration = CSV4PojoUtils.getCsvClassConfiguration(clazz);
    }

    @Override
    public void writeCSVOutputStream(OutputStream outputStream) {
        // performing mandatory validation of arguments
        validateCSVOutputStream(csvClassConfiguration.getClazz(), outputStream);
        abstractWriteCSVOutputStream(outputStream);
    }

    @Override
    public <T> void writeCSVOutputStream(List<T> pojoList, OutputStream outputStream) {
        // performing mandatory validation of arguments
        validateCSVOutputStream(csvClassConfiguration.getClazz(), pojoList, outputStream);
        abstractWriteCSVOutputStream(pojoList, outputStream);
    }

    @Override
    public <T> void writeCSVOutputStream(Stream<T> pojoStream, OutputStream outputStream) {
        // performing mandatory validation of arguments
        validateCSVOutputStream(csvClassConfiguration.getClazz(), pojoStream, outputStream);
        abstractWriteCSVOutputStream(pojoStream, outputStream);
    }

    public CsvClassConfiguration<?> getCsvClassConfiguration() {
        return csvClassConfiguration;
    }

    protected abstract void abstractWriteCSVOutputStream(OutputStream outputStream);

    protected abstract void abstractWriteCSVOutputStream(List<?> pojoList, OutputStream outputStream);

    protected abstract void abstractWriteCSVOutputStream(Stream<?> pojoStream, OutputStream outputStream);
}

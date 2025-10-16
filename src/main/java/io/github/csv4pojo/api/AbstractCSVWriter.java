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
    protected final CsvClassConfiguration csvClassConfiguration;

    protected <T> AbstractCSVWriter(Class<T> clazz) {
        if (null == clazz)
            throw new IllegalArgumentException("Class parameter is required");
        this.csvClassConfiguration = CSV4PojoUtils.getCsvClassConfiguration(clazz);
    }

    @Override
    public <T> void writeCSVOutputStream(Class<T> clazz, OutputStream outputStream) {
        // performing validation of arguments
        validateCSVOutputStream(clazz, outputStream);
        abstractWriteCSVOutputStream(clazz, outputStream);
    }

    @Override
    public <T> void writeCSVOutputStream(Class<T> clazz, List<T> pojoList, OutputStream outputStream) {
        // performing validation of arguments
        validateCSVOutputStream(clazz, pojoList, outputStream);
        abstractWriteCSVOutputStream(clazz, pojoList, outputStream);
    }

    @Override
    public <T> void writeCSVOutputStream(Class<T> clazz, Stream<T> pojoStream, OutputStream outputStream) {
        // performing validation of arguments
        validateCSVOutputStream(clazz, pojoStream, outputStream);
        abstractWriteCSVOutputStream(clazz, pojoStream, outputStream);
    }

    protected abstract <T> void abstractWriteCSVOutputStream(Class<T> clazz, OutputStream outputStream);

    protected abstract <T> void abstractWriteCSVOutputStream(Class<T> clazz, List<T> pojoList, OutputStream outputStream);

    protected abstract <T> void abstractWriteCSVOutputStream(Class<T> clazz, Stream<T> pojoStream, OutputStream outputStream);
}

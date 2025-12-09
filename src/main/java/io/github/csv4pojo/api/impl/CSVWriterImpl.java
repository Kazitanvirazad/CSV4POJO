package io.github.csv4pojo.api.impl;

import io.github.csv4pojo.annotation.FieldType;
import io.github.csv4pojo.api.AbstractCSVWriter;
import io.github.csv4pojo.exception.MisConfiguredClassFieldException;
import io.github.csv4pojo.exception.StreamException;
import io.github.csv4pojo.function.AbstractCSVOutputStreamWriterConsumer;
import io.github.csv4pojo.model.CsvClassField;
import io.github.csv4pojo.utils.CSV4PojoUtils;
import io.github.csv4pojo.utils.FieldReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static io.github.csv4pojo.common.CommonConstants.COMMA;
import static io.github.csv4pojo.common.CommonConstants.EMPTY_STRING;
import static io.github.csv4pojo.common.CommonConstants.ONE_DOUBLE_QUOTES;
import static io.github.csv4pojo.common.CommonConstants.TWO_DOUBLE_QUOTES;
import static io.github.csv4pojo.utils.CSV4PojoUtils.charBufferSize;

/**
 * @author Kazi Tanvir Azad
 */
public class CSVWriterImpl extends AbstractCSVWriter {

    private final int charBufferSize;

    /**
     * Constructs CSVWriter with preferred Output-buffer and Class type
     *
     * @param <T>            the class of the value
     * @param clazz          {@code Class<T>} Class type to be used for csv writer
     * @param charBufferSize Output-buffer size, a positive integer
     */
    public <T> CSVWriterImpl(Class<T> clazz, int charBufferSize) {
        super(clazz);
        this.charBufferSize = charBufferSize;
    }

    /**
     * Constructs CSVWriter with default or preset in environment Output-buffer size and Class type.
     * Setting CHAR_BUFFER_SIZE in environment will be taken as first preference, otherwise
     * fallback value 8192 will be considered
     *
     * @param <T>   the class of the value
     * @param clazz {@code Class<T>} Class type to be used for csv writer
     */
    public <T> CSVWriterImpl(Class<T> clazz) {
        super(clazz);
        this.charBufferSize = charBufferSize();
    }

    /**
     * Writes List of Java object mapped with the given java class annotated with {@link FieldType} annotation field
     * values in to the outputStream
     *
     * @param pojoList     {@code List<T>}
     * @param outputStream {@link OutputStream}
     */
    @Override
    protected void abstractWriteCSVOutputStream(List<?> pojoList, OutputStream outputStream) {
        writeToCSVOutputStream(pojoList.stream(), outputStream);
    }

    /**
     * Writes Stream of Java object mapped with the given java class annotated with {@link FieldType} annotation field
     * values in to the outputStream
     *
     * @param pojoStream   {@code Stream <T>}
     * @param outputStream {@link OutputStream}
     */
    @Override
    protected void abstractWriteCSVOutputStream(Stream<?> pojoStream, OutputStream outputStream) {
        writeToCSVOutputStream(pojoStream, outputStream);
    }

    /**
     * Writes an empty csv file in to the OutputStream with all the headers mapped with the given java class annotated
     * with {@link FieldType} annotation field names
     *
     * @param outputStream {@link OutputStream}
     */
    @Override
    protected void abstractWriteCSVOutputStream(OutputStream outputStream) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            // Writing the header elements to the OutputStream
            writeHeaderToOutputStream(writer);
        } catch (IOException exception) {
            throw new StreamException("OutputStream is invalid or null: ", exception);
        }
    }

    /**
     * Writes Stream of Java object mapped with the given java class annotated with {@link FieldType} annotation field
     * values in to the outputStream
     *
     * @param pojoStream   {@code Stream<T>}
     * @param outputStream {@link OutputStream}
     */
    private <T> void writeToCSVOutputStream(Stream<T> pojoStream, OutputStream outputStream) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream), charBufferSize)) {
            // Writing the header elements to the OutputStream
            writeHeaderToOutputStream(writer);
            // Reading each object from Stream and execute logic to create line elements and write in to the BufferedWriter
            CSVOutputStreamWriterConsumer<T> writeCsvOutputStreamConsumer = new CSVOutputStreamWriterConsumer<>(writer);
            pojoStream.filter(Objects::nonNull).forEach(writeCsvOutputStreamConsumer);
        } catch (IOException exception) {
            throw new StreamException("OutputStream is invalid or null: ", exception);
        }
    }

    /**
     * Reads all the fields annotated with {@link FieldType} annotation of the Java object passed in the method
     * parameter and creates and returns a List of the values
     *
     * @param pojo {@link T}
     * @return {@code  List<String>}
     */
    private <T> List<String> readCsvFieldValuesFromPojo(final T pojo) {
        List<String> lineElements = new ArrayList<>();
        List<String> headers = csvClassConfiguration.getCsvHeaders();
        Map<String, CsvClassField> csvClassFieldMap = csvClassConfiguration.getCsvClassFieldMap();
        for (String header : headers) {
            CsvClassField csvClassField = csvClassFieldMap.getOrDefault(header, null);
            if (null == csvClassField) {
                throw new MisConfiguredClassFieldException("Improper class field mapping for : " + header);
            }
            FieldReader fieldReader = CSV4PojoUtils.getFieldReader(csvClassField);
            String fieldValue = fieldReader.read(csvClassField, pojo);
            lineElements.add(fieldValue);
        }
        return lineElements;
    }

    /**
     * Writes csv header elements in to the BufferedWriter and adds a new line
     *
     * @param writer {@link  BufferedWriter}
     * @throws IOException If an I/O error occurs
     */
    private <T> void writeHeaderToOutputStream(BufferedWriter writer) throws IOException {
        String formattedHeaderElement = getFormattedLineElements(this.csvClassConfiguration.getCsvHeaders());
        writer.write(formattedHeaderElement);
        writer.newLine();
    }

    /**
     * Adds extra special characters such as double quotes for MS Excel, Google Sheet or Libre Calc compatibility
     *
     * @param lineElements {@link List<String>}
     * @return {@link String}
     */
    private String getFormattedLineElements(List<String> lineElements) {
        StringBuilder formattedLine = new StringBuilder();
        for (int i = 0; i < lineElements.size(); i++) {
            String element = lineElements.get(i);
            if (element == null || element.equals(EMPTY_STRING)) {
                formattedLine.append(EMPTY_STRING);
                if (i < lineElements.size() - 1) {
                    formattedLine.append(COMMA);
                }
                continue;
            }
            if (element.contains(ONE_DOUBLE_QUOTES) || element.contains(COMMA)) {
                element = element.replace(ONE_DOUBLE_QUOTES, TWO_DOUBLE_QUOTES);
                element = wrapToFormatElement(element);
            }
            formattedLine.append(element);
            if (i < lineElements.size() - 1) {
                formattedLine.append(COMMA);
            }
        }
        return formattedLine.toString();
    }

    /**
     * Appends and prepends double quotes characters
     *
     * @param element {@link String}
     * @return {@link String}
     */
    private String wrapToFormatElement(String element) {
        return ONE_DOUBLE_QUOTES + element + ONE_DOUBLE_QUOTES;
    }

    private class CSVOutputStreamWriterConsumer<T> extends AbstractCSVOutputStreamWriterConsumer<T> {

        CSVOutputStreamWriterConsumer(BufferedWriter writer) {
            super(writer);
        }

        @Override
        public void accept(T pojo) {
            // Reading the value of fields annotated with @FieldType annotation from java object and putting it in to a List
            List<String> lineElements = readCsvFieldValuesFromPojo(pojo);

            // Converting the List of line elements to a CSV row format
            String formattedLineElement = getFormattedLineElements(lineElements);

            try {
                // Writing the csv formatted row in to the BufferedWriter
                writer.write(formattedLineElement);

                // Appending a line in to the BufferedWriter
                writer.newLine();
            } catch (IOException exception) {
                throw new StreamException("OutputStream is invalid or null: ", exception);
            }
        }
    }
}

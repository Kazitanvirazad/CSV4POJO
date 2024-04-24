package org.csv4pojoparser.util.test;

import org.csv4pojoparser.util.CSVWriter;
import org.csv4pojoparser.util.impl.CSVWriterImpl;
import org.csv4pojoparser.util.test.model.Computer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class AppWriteTest {
    public static void main(String[] args) {
        CSVWriter csvWriter = new CSVWriterImpl();
        try {
            OutputStream outputStream = new FileOutputStream(new File("data/output/computerdata.csv"));
            csvWriter.createEmptyCSVOutputStreamFromClass(Computer.class, outputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

package io.github.csv4pojo.function;

import java.io.BufferedWriter;
import java.util.function.Consumer;

/**
 * @author Kazi Tanvir Azad
 */
public abstract class AbstractCSVOutputStreamWriterConsumer<T> implements Consumer<T> {
    protected final BufferedWriter writer;

    protected AbstractCSVOutputStreamWriterConsumer(BufferedWriter writer) {
        this.writer = writer;
    }
}

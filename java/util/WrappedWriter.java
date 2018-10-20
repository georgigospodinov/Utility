package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WrappedWriter {
    private Logger l;
    private BufferedWriter writer;

    private void defaultCatch(Exception e) {
        if (l == null) e.printStackTrace();
        else l.log(e);
    }

    public WrappedWriter(String filename, Logger l) {
        this.l = l;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
        }
        catch (IOException e) {
            defaultCatch(e);
        }
    }

    public WrappedWriter(String filename) {
        this(filename, null);
    }

    public void write(String s) {
        try {
            writer.write(s);
        }
        catch (IOException e) {
            defaultCatch(e);
        }
    }

    public void writeLine(String s) {
        write(s);
        newLine();
    }

    public void write(int c) {
        try {
            writer.write(c);
        }
        catch (IOException e) {
            defaultCatch(e);
        }
    }

    public void newLine() {
        try {
            writer.newLine();
        }
        catch (IOException e) {
            defaultCatch(e);
        }
    }

    public void flush() {
        try {
            writer.flush();
        }
        catch (IOException e) {
            defaultCatch(e);
        }
    }

    public void close() {
        try {
            writer.close();
        }
        catch (IOException e) {
            defaultCatch(e);
        }
    }

}

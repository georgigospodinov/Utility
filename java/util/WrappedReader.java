package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Wraps {@link BufferedReader}, so that methods can be called without having to try-catch.
 *
 * @version 1.0
 */
public class WrappedReader {
    private Logger l;
    private BufferedReader reader;

    private void defaultCatch(Exception e) {
        if (l == null) e.printStackTrace();
        else l.log(e);
    }

    /**
     * Opens a {@link BufferedReader} to the given filename.
     *
     * @param filename filename of input file
     * @param l        {@link Logger} to be used for logging {@link IOException}s.
     */
    public WrappedReader(String filename, Logger l) {
        this.l = l;
        try {
            reader = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException e) {
            defaultCatch(e);
        }
    }

    /**
     * Opens a {@link BufferedReader} to the given filename.
     * {@link IOException}s will be printed to standard error.
     * This method is equivalent to calling {@link WrappedReader#WrappedReader(String, Logger)} with null as the second argument,
     *
     * @param filename filename of input file
     */
    public WrappedReader(String filename) {
        this(filename, null);
    }

    /**
     * Reads a line of text. A line is considered to be terminated by any one of a
     * linefeed ('\n'), a carriage return ('\r'), or a carriage return followed immediately by a linefeed.
     *
     * @return A String containing the contents of the line, not including any line-termination characters,
     * or null if the end of the stream has been reached
     */
    public String readLine() {
        try {
            return reader.readLine();
        }
        catch (IOException e) {
            defaultCatch(e);
        }
        return null;
    }

    /**
     * Reads a single character. (Unicode?)
     *
     * @return The character read, as an integer in the range 0 to 65535 (0x00-0xffff), or -1 if the end of the stream has been reached
     */
    public int read() {
        try {
            return reader.read();
        }
        catch (IOException e) {
            defaultCatch(e);
        }
        return -1;
    }

    /**
     * Skips characters.
     *
     * @param n The number of characters to skip
     * @return The number of characters actually skipped
     */
    public long skip(long n) {
        if (n < 0)
            throw new IllegalArgumentException("Cannot skip a negative number of characters!");

        try {
            return reader.skip(n);
        }
        catch (IOException e) {
            defaultCatch(e);
        }
        return n;
    }

    /**
     * Tells whether this stream is ready to be read.
     * A buffered character stream is ready if the buffer is not empty, or if the underlying character stream is ready.
     *
     * @return True if the next read() is guaranteed not to block for input, false otherwise. Note that returning false does not guarantee that the next read will block.
     */
    public boolean ready() {
        try {
            return reader.ready();
        }
        catch (IOException e) {
            defaultCatch(e);
        }
        return false;
    }

    /**
     * Returns a Stream, the elements of which are lines read from this {@link WrappedReader}.
     *
     * @return a Stream<String> providing the lines of text described by this {@link WrappedReader}
     */
    public Stream<String> lines() {
        return reader.lines();
    }

    /**
     * Closes the stream and releases any system resources associated with it.
     */
    public void close() {
        try {
            reader.close();
        }
        catch (IOException e) {
            defaultCatch(e);
        }
    }

    /**
     * Resets the stream to the most recent mark.
     */
    public void reset() {
        try {
            reader.reset();
        }
        catch (IOException e) {
            defaultCatch(e);
        }
    }

    /**
     * Marks the present position in the stream.
     * Subsequent calls to reset() will attempt to reposition the stream to this point.
     *
     * @param readAheadLimit Limit on the number of characters that may be read while still preserving the mark.
     *                       An attempt to reset the stream after reading characters up to this limit or beyond may fail.
     *                       A limit value larger than the size of the input buffer
     *                       will cause a new buffer to be allocated whose size is no smaller than limit.
     *                       Therefore large values should be used with care.
     */
    public void mark(int readAheadLimit) {
        try {
            reader.mark(readAheadLimit);
        }
        catch (IOException e) {
            defaultCatch(e);
        }
    }

    /**
     * Tells whether this stream supports the mark() operation, which it does.
     *
     * @return true if and only if this stream supports the mark operation.
     */
    public boolean markSupported() {
        return reader.markSupported();
    }
}
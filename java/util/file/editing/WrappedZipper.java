package util.file.editing;

import util.log.Logger;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

/**
 * Provides zipping of files and folders without having to try-catch.
 * Wraps {@link ZipOutputStream}.
 *
 * @author 150009974
 * @version 0.1
 */
public class WrappedZipper {

    /** The amount of bytes read at a time. */
    private static final int CHUNK_SIZE = 1024;

    /** A {@link Logger} to write all errors to. */
    private Logger l;

    /** The wrapped {@link ZipOutputStream} instance. */
    private ZipOutputStream zout;

    /**
     * Opens a {@link ZipOutputStream} to the specified file.
     *
     * @param zipFilename the name of the zip file
     * @param logger      {@link Logger} used for logging {@link IOException}s
     */
    public WrappedZipper(final String zipFilename, final Logger logger) {
        this.l = logger;
        try {
            zout = new ZipOutputStream(new FileOutputStream(zipFilename));
        } catch (FileNotFoundException e) {
            defaultCatch(e);
        }
    }

    /**
     * The default behaviour for caught {@link Exception}s.
     *
     * @param e the caught {@link Exception}
     */
    private void defaultCatch(final Exception e) {
        if (l == null) {
            e.printStackTrace();
        } else {
            l.log(e);
        }
    }

    /** Closes the ZIP output stream as well as the stream being filtered. */
    public void close() {
        try {
            zout.close();
        } catch (IOException e) {
            defaultCatch(e);
        }
    }

}

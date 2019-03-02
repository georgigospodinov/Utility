package util.file.editing;

import util.log.Logger;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

/**
 * Provides unzipping of files and folders without having to try-catch.
 * Wraps {@link ZipInputStream}.
 *
 * @version 0.1
 */
public class WrappedUnzipper {

    /** The amount of bytes read at a time. */
    private static final int CHUNK_SIZE = 1024;

    /** A {@link Logger} to write all errors to. */
    private Logger l;

    /** The wrapped {@link ZipInputStream} instance. */
    private ZipInputStream zipIn;

    /** The directory to write to. */
    private File destDir;

    /**
     * Instantiates a {@link WrappedUnzipper} that will unzip all
     * given files to the specified directory.
     *
     * @param destinationDir the directory to unzip to
     * @param logger         Logger used for logging {@link IOException}s
     */
    public WrappedUnzipper(final File destinationDir, final Logger logger) {
        this.l = logger;
        this.destDir = destinationDir;
    }

    /**
     * Instantiates a {@link WrappedUnzipper} that will unzip all
     * given files to the specified directory.
     * This is equivalent to <code>WrappedUnzipper(destinationDir, null)</code>.
     *
     * @param destinationDir the directory to unzip to
     */
    public WrappedUnzipper(final File destinationDir) {
        this(destinationDir, null);
    }

    /**
     * Instantiates a {@link WrappedUnzipper} that will unzip all
     * given files to the specified directory.
     * This is equivalent to
     * <code>WrappedUnzipper(new File(destinationDir, logger)</code>.
     *
     * @param destinationDir the name of the directory to unzip to
     * @param logger         Logger used for logging {@link IOException}s
     */
    public WrappedUnzipper(final String destinationDir, final Logger logger) {
        this(new File(destinationDir), logger);
    }

    /**
     * Instantiates a {@link WrappedUnzipper} that will unzip all
     * given files to the specified directory.
     * This is equivalent to
     * <code>WrappedUnzipper(new File(destinationDir)</code>.
     *
     * @param destinationDir the name of the directory to unzip to
     */
    public WrappedUnzipper(final String destinationDir) {
        this(new File(destinationDir));
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

}

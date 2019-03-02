package util.file.editing;

import util.PrintFormatting;
import util.log.Logger;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

/**
 * Provides unzipping of files and folders without having to try-catch.
 * Wraps {@link ZipInputStream}.
 *
 * @version 1.0
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

    /**
     * Tests the {@link WrappedUnzipper} by unzipping the whole util package.
     *
     * @param args arguments are ignored
     */
    public static void main(final String[] args) {
        WrappedUnzipper unzipper = new WrappedUnzipper("assets/temp");
        PrintFormatting.print(unzipper.unzip("assets/util.zip"));
    }

    /**
     * Instantiates the zip reader {@link WrappedUnzipper#zipIn}.
     *
     * @param filename the name of zip file to read
     */
    private void instantiateZipReader(final String filename) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            defaultCatch(e);
            return;
        }
        zipIn = new ZipInputStream(fis);
    }

    /**
     * Creates and returns the file corresponding to the specified zipEntry.
     *
     * @param zipEntry the {@link ZipEntry} to create a file for
     * @return the created file
     */
    private File createFileFor(final ZipEntry zipEntry) {
        File destFile = new File(destDir, zipEntry.getName());
        if (zipEntry.isDirectory()) {
            destFile.mkdir();
        } else {
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                defaultCatch(e);
            }
        }
        return destFile;
    }

    /**
     * Unzips the current {@link ZipEntry} into the specified file.
     * if the specified file is not a regular file, it is ignored.
     *
     * @param file the file to write to
     */
    private void unzipFile(final File file) {
        if (!file.isFile()) {
            return;
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            defaultCatch(e);
            return;
        }
        byte[] buf = new byte[CHUNK_SIZE];
        try {
            for (int len = zipIn.read(buf); len >= 0; len = zipIn.read(buf)) {
                fos.write(buf, 0, len);
            }
        } catch (IOException e) {
            defaultCatch(e);
        }
        try {
            fos.close();
        } catch (IOException e) {
            defaultCatch(e);
        }
    }

    /**
     * Unzips the specified zip file.
     * Returns the set of unzipped entries as defined by
     * {@link ZipEntry#toString()}.
     *
     * @param filename the name of the zip file
     * @return the set of all unzipped entries
     */
    public LinkedHashSet<String> unzip(final String filename) {
        LinkedHashSet<String> unzippedEntries = new LinkedHashSet<>();
        instantiateZipReader(filename);
        try {
            ZipEntry zipEntry = zipIn.getNextEntry();
            while (zipEntry != null) {
                unzippedEntries.add(zipEntry.toString());
                File file = createFileFor(zipEntry);
                unzipFile(file);
                zipIn.closeEntry();
                zipEntry = zipIn.getNextEntry();
            }
        } catch (IOException e) {
            defaultCatch(e);
        }
        try {
            zipIn.close();
        } catch (IOException e) {
            defaultCatch(e);
        }
        return unzippedEntries;
    }

}

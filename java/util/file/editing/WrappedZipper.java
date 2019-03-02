package util.file.editing;

import util.log.Logger;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

/**
 * Provides zipping of files and folders without having to try-catch.
 * Wraps {@link ZipOutputStream}.
 *
 * @version 1.0
 */
public class WrappedZipper {

    /** The amount of bytes read at a time. */
    private static final int CHUNK_SIZE = 1024;

    /** A {@link Logger} to write all errors to. */
    private Logger l;

    /** The wrapped {@link ZipOutputStream} instance. */
    private ZipOutputStream zipOut;

    /**
     * Opens a {@link ZipOutputStream} to the specified file.
     *
     * @param zipFilename the name of the zip file
     * @param logger      {@link Logger} used for logging {@link IOException}s
     */
    public WrappedZipper(final String zipFilename, final Logger logger) {
        this.l = logger;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFilename));
        } catch (FileNotFoundException e) {
            defaultCatch(e);
        }
    }

    /**
     * Opens a {@link ZipOutputStream} to the specified file.
     * {@link IOException}s will be printed to standard error.
     * This is equivalent to <code>WrappedZipper(zipFilename, null)</code>.
     *
     * @param zipFilename the name of the zip file
     */
    public WrappedZipper(final String zipFilename) {
        this(zipFilename, null);
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
            zipOut.close();
        } catch (IOException e) {
            defaultCatch(e);
        }
    }

    /**
     * Zips the specified file.
     * If a regular file is specified, it becomes the only file in the zip.
     * If a directory is specified, all files in it are recursively zipped.
     * The specified directory is at the top of zip.
     *
     * @param filename th name of the file to zip
     */
    public void zip(final String filename) {
        File file = new File(filename);
        recursiveZip(file, file.getName());
    }

    /**
     * Recursively zips the specified file.
     *
     * @param file the {@link File} object to zip
     * @param name the name with which the file will appear in the zip
     */
    private void recursiveZip(final File file, final String name) {
        String zipEntryName = putEntry(file, name);
        if (file.isDirectory()) {
            zipDirectory(file, zipEntryName);
        } else {
            zipRegular(file);
        }
    }

    /**
     * Puts a new {@link ZipEntry} for the specified file to the specified name.
     * If the file is a regular file, the specified name is used as entry name.
     * If the file is a directory, this method guarantees that the entry name
     * appends "/" to the specified name if needed.
     * Returns the entry name that was used.
     *
     * @param file the file to put an entry for
     * @param name the name to use as the entry
     * @return the entry name that was used
     */
    private String putEntry(final File file, final String name) {
        String zipEntryName;
        if (file.isDirectory() && !name.endsWith("/")) {
            zipEntryName = name + "/";
        } else {
            zipEntryName = name;
        }
        try {
            zipOut.putNextEntry(new ZipEntry(zipEntryName));
        } catch (IOException e) {
            defaultCatch(e);
        }
        return zipEntryName;
    }

    /**
     * Zips the specified directory.
     *
     * @param directory    the directory to zip
     * @param zipEntryName the entry name of the directory in the zip
     */
    private void zipDirectory(final File directory, final String zipEntryName) {
        try {
            zipOut.closeEntry();
        } catch (IOException e) {
            defaultCatch(e);
        }
        File[] children = directory.listFiles();
        assert children != null;
        for (File childFile : children) {
            recursiveZip(childFile, zipEntryName + childFile.getName());
        }
    }

    /**
     * Reads and zips all bytes from the specified {@link File}.
     *
     * @param file the stream to read and zip
     */
    private void zipRegular(final File file) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            defaultCatch(e);
            return;
        }
        byte[] bytes = new byte[CHUNK_SIZE];
        try {
            for (int len = fis.read(bytes); len >= 0; len = fis.read(bytes)) {
                zipOut.write(bytes, 0, len);
            }
        } catch (IOException e) {
            defaultCatch(e);
        }
        try {
            zipOut.closeEntry();
        } catch (IOException e) {
            defaultCatch(e);
        }
        try {
            fis.close();
        } catch (IOException e) {
            defaultCatch(e);
        }

    }

    /**
     * Tests the {@link WrappedZipper} by zipping the whole util package.
     *
     * @param args arguments are ignored
     */
    public static void main(final String[] args) {
        WrappedZipper zipper = new WrappedZipper("assets/util.zip");
        zipper.zip("java/util");
        zipper.zip("test/junit");
        zipper.close();
    }

}

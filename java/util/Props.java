package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Provides reading of ".props" files.
 *
 * @version 1.3
 */
public class Props {

    private static final HashMap<String, String> PROPERTIES = new HashMap<>();
    private static final String DEFAULT_PROPS_FILE = "default.props";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String COMMENT_SYMBOL = "#";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    /**
     * Gives the number of properties currently in storage.
     *
     * @return the current number of properties
     */
    public static int size() {
        return PROPERTIES.size();
    }

    /**
     * Clears all the loaded properties.
     */
    public static void clear() {
        PROPERTIES.clear();
    }

    /**
     * Get the value associated with the given property.
     *
     * @param property the property to look for
     * @return the value associated with that property
     */
    public static String get(String property) {
        if (!PROPERTIES.containsKey(property))
            throw new NullPointerException("No property \"" + property + "\"");

        return PROPERTIES.get(property);
    }

    public static void load() throws FileNotFoundException {
        load(DEFAULT_PROPS_FILE);
    }

    /**
     * Load properties from the specified file.
     *
     * @param filename the name of the file containing properties.
     *                 This can be absolute or relative path.
     * @throws FileNotFoundException if there is no file with the given name
     */
    public static void load(String filename) throws FileNotFoundException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find props file \"" + filename + "\"");
        }

        reader.lines().forEach(line -> {
            // Skip empty lines and comments
            if (line.isEmpty() || line.startsWith(COMMENT_SYMBOL)) return;

            String [] kv = line.split(KEY_VALUE_SEPARATOR);
            PROPERTIES.put(kv[KEY_INDEX], kv[VALUE_INDEX]);
        });
    }

}

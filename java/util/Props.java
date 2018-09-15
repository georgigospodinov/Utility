package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class Props {

    public static final HashMap<String, String> PROPERTIES = new HashMap<>();
    private static final String DEFAULT_PROPS_FILE = "constants.props";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static String get(String property) {
        if (!PROPERTIES.containsKey(property))
            throw new NullPointerException("No property \"" + property + "\"");

        return PROPERTIES.get(property);
    }

    public static void load() throws FileNotFoundException {
        load(DEFAULT_PROPS_FILE);
    }

    public static void load(String filename) throws FileNotFoundException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find props file \"" + filename + "\"");
        }

        reader.lines().forEach(line -> {
            String [] kv = line.split(KEY_VALUE_SEPARATOR);
            PROPERTIES.put(kv[KEY_INDEX], kv[VALUE_INDEX]);
        });
    }

}

package junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Props;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PropsTest {

    private static final String FILE = "assets/test.props";

    @BeforeEach
    void load() throws FileNotFoundException {
        Props.load(FILE);
    }

    @Test
    void testLoad() {
        assertEquals(Props.getString("CONSTANT_HELLO"), "HELLO");
        assertEquals(Props.getInt("CONSTANT_ONE"), 1);
        assertEquals(Props.getDouble("CONSTANT_PI"), 3.14);
        assertEquals(Props.getString("CONSTANT MULTI WORD STRING"), "Hello World!");
        assertEquals(Props.getString("Hello in BG"), "Здравей!");
        assertEquals(Props.getDouble("sample"), 1.0);
        assertEquals(Props.size(), 8);
    }

    @Test
    void testMissing() {
        assertThrows(NullPointerException.class, () -> {
            Props.getLong("sample");
        });
        assertThrows(NullPointerException.class, () -> {
            Props.getString("CONSTANT_ONE");
        });
        assertThrows(NullPointerException.class, () -> {
            Props.getString("nothing");
        });
    }

    @Test
    void testIntLong() {
        assertEquals(Props.getInt("my int"), 2);
        assertEquals(Props.getLong("my long"), 9223372036854775807L);
        assertThrows(NullPointerException.class, () -> Props.getInt("my long"));
        assertThrows(NullPointerException.class, () -> Props.getLong("my int"));
    }

    @Test
    void testClear() {
        Props.clear();
        assertEquals(Props.size(), 0);
    }
}
package junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Props;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals(Props.getInt("NEGATIVE"), -3);
        assertEquals(Props.getDouble("CONSTANT_PI"), 3.14f);
        assertEquals(Props.getString("CONSTANT MULTI WORD STRING"), "Hello World!");
        assertEquals(Props.getString("Hello in BG"), "Здравей!");
        assertEquals(Props.getDouble("sample"), 1.0);
        assertEquals(Props.getFloat("sample"), 1.0);
        assertEquals(Props.size(), 11);
    }

    @Test
    void testMissing() {
        assertThrows(NullPointerException.class, () -> Props.getLong("sample"));
        assertThrows(NullPointerException.class, () -> Props.getString("CONSTANT_ONE"));
        assertThrows(NullPointerException.class, () -> Props.getString("nothing"));
        assertThrows(NullPointerException.class, () -> Props.getString("my double"));
        assertThrows(NullPointerException.class, () -> Props.getString("my long"));
    }

    @Test
    void testIntLong() {
        assertEquals(Props.getInt("my int"), 2);
        assertEquals(Props.getLong("my long"), 9223372036854775807L);
        assertEquals(Props.getLong("my int"), 2);
        assertThrows(NullPointerException.class, () -> Props.getInt("my long"));
    }

    @Test
    void testDoubleLong() {
        assertEquals(Props.getFloat("my float"), -0.3f);
        assertEquals(Props.getDouble("my float"), -0.3f);
        assertEquals(Props.getDouble("my double"), 1.797693E41);
        assertThrows(NullPointerException.class, () -> Props.getFloat("my double"));
    }

    @Test
    void testClear() {
        Props.clear();
        assertEquals(Props.size(), 0);
    }
}
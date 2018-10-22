package junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Props;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropsTest {

    private static final String FILE = "assets/test.props";
    private static Props p;

    @BeforeEach
    void load() throws FileNotFoundException {
        p = new Props();
        p.load(FILE);
    }

    @Test
    void testLoad() {
        assertEquals(p.getString("CONSTANT_HELLO"), "HELLO");
        assertEquals(p.getInt("CONSTANT_ONE"), 1);
        assertEquals(p.getInt("NEGATIVE"), -3);
        assertEquals(p.getDouble("CONSTANT_PI"), 3.14f);
        assertEquals(p.getString("CONSTANT MULTI WORD STRING"), "Hello World!");
        assertEquals(p.getString("Hello in BG"), "Здравей!");
        assertEquals(p.getDouble("sample"), 1.0);
        assertEquals(p.getFloat("sample"), 1.0);
        assertEquals(p.size(), 11);
    }

    @Test
    void testMissing() {
        assertThrows(NullPointerException.class, () -> p.getLong("sample"));
        assertThrows(NullPointerException.class, () -> p.getString("CONSTANT_ONE"));
        assertThrows(NullPointerException.class, () -> p.getString("nothing"));
        assertThrows(NullPointerException.class, () -> p.getString("my double"));
        assertThrows(NullPointerException.class, () -> p.getString("my long"));
    }

    @Test
    void testIntLong() {
        assertEquals(p.getInt("my int"), 2);
        assertEquals(p.getLong("my long"), 9223372036854775807L);
        assertEquals(p.getLong("my int"), 2);
        assertThrows(NullPointerException.class, () -> p.getInt("my long"));
    }

    @Test
    void testDoubleLong() {
        assertEquals(p.getFloat("my float"), -0.3f);
        assertEquals(p.getDouble("my float"), -0.3f);
        assertEquals(p.getDouble("my double"), 1.797693E41);
        assertThrows(NullPointerException.class, () -> p.getFloat("my double"));
    }

    @Test
    void testClear() {
        p.clear();
        assertEquals(p.size(), 0);
    }
}
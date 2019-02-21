package junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Props;

import java.awt.*;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("HELLO", p.getString("CONSTANT_HELLO"));
        assertEquals(1, p.getInt("CONSTANT_ONE"));
        assertEquals(-3, p.getInt("NEGATIVE"));
        assertEquals(3.14f, p.getDouble("CONSTANT_PI"));
        assertEquals("Hello World!", p.getString("CONSTANT MULTI WORD STRING"));
        assertEquals("Здравей!", p.getString("Hello in BG"));
        assertEquals(1.0, p.getDouble("sample"));
        assertEquals(1.0, p.getFloat("sample"));
        assertEquals(15, p.size());
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
        assertEquals(2, p.getInt("my int"));
        assertEquals(9223372036854775807L, p.getLong("my long"));
        assertEquals(2, p.getLong("my int"));
        assertThrows(NullPointerException.class, () -> p.getInt("my long"));
    }

    @Test
    void testFloatDouble() {
        assertEquals(-0.3f, p.getFloat("my float"));
        assertEquals(-0.3f, p.getDouble("my float"));
        assertEquals(1.797693E41, p.getDouble("my double"));
        assertThrows(NullPointerException.class, () -> p.getFloat("my double"));
    }

    @Test
    void testAnyNumber() {
        assertEquals(2, p.getAnyInt("my int"));
        assertEquals((int) 9223372036854775807L, p.getAnyInt("my long"));
        assertEquals(0, p.getAnyInt("my float"));
        assertEquals((int) 1.797693E41, p.getAnyInt("my double"));
    }

    @Test
    void testTrue() {
        assertTrue(p.isTrue("my boolean"));
        assertFalse(p.isTrue("CONSTANT_HELLO"));
        assertThrows(NullPointerException.class, () -> p.isTrue("my int"));
    }

    @Test
    void testColor() {
        assertEquals(new Color(100, 20, 250), p.getColor("my color"));
        assertThrows(NullPointerException.class, () -> p.getColor("my int"));
    }

    @Test
    void testClear() {
        p.clear();
        assertEquals(0, p.size());
    }

    @Test
    void testForEachInteger() {
        p.forEachInteger((key, value) -> {
            switch (key) {
                case "CONSTANT_ONE":
                    assertEquals(1, (int) value);
                    break;
                case "NEGATIVE":
                    assertEquals(-3, (int) value);
                    break;
                case "my int":
                    assertEquals(2, (int) value);
                    break;
            }
        });
    }

    @Test
    void testForEachString() {
        p.forEachString((key, value) -> {
            switch (key) {
                case "CONSTANT_HELLO":
                    assertEquals("HELLO", value);
                    break;
                case "CONSTANT MULTI WORD STRING":
                    assertEquals("Hello World!", value);
                    break;
                case "Hello in BG":
                    assertEquals("Здравей!", value);
                    break;
            }
        });
    }
}
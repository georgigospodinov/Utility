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
        assertEquals(Props.get("CONSTANT_HELLO"), "HELLO");
        assertEquals(Props.get("CONSTANT_ONE"), "1");
        assertEquals(Props.get("CONSTANT_PI"), "3.14");
        assertEquals(Props.get("CONSTANT MULTI WORD STRING"), "Hello World!");
        assertEquals(Props.get("Hello in BG"), "Здравей!");
        assertEquals(Props.size(), 5);
    }

    @Test
    void testClear() {
        Props.clear();
        assertEquals(Props.size(), 0);
    }
}
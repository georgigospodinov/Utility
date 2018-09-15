package junit;

import org.junit.jupiter.api.Test;
import util.Props;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PropsTest {

    private static final String FILE = "assets/test.props";

    @Test
    void load() throws FileNotFoundException {
        Props.load(FILE);
        assertEquals(Props.get("CONSTANT_HELLO"), "HELLO");
        assertEquals(Props.get("CONSTANT_ONE"), "1");
        assertEquals(Props.get("CONSTANT_PI"), "3.14");
        assertEquals(Props.get("CONSTANT MULTI WORD STRING"), "Hello World!");
        assertEquals(Props.get("Hello in BG"), "Здравей!");
        assertEquals(Props.size(), 5);
    }
}
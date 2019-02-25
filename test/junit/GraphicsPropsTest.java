package junit;

import org.junit.jupiter.api.*;
import util.*;

import java.awt.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GraphicsPropsTest {

    private static final String FILE = "assets/graphics.props";
    private static GraphicsProps p;

    @BeforeEach
    void load() throws FileNotFoundException {
        p = new GraphicsProps();
        p.load(FILE);
    }

    @Test
    void testColor() {
        assertEquals(new Color(100, 20, 250), p.getColor("my color"));
        assertThrows(NullPointerException.class, () -> p.getColor("my int"));
    }

    @Test
    void testPoint() {
        assertEquals(new Point(10, 30), p.getPoint("my point"));
        assertThrows(NullPointerException.class, () -> p.getPoint("my color"));
    }

    @Test
    void testDimension() {
        assertEquals(new Dimension(120, 105), p.getDimension("my dimension"));
        assertThrows(NullPointerException.class, () -> p.getPoint("my color"));
    }

    @Test
    void testFont() {
        assertEquals(new Font("Dialog", Font.BOLD, 12), p.getFont("my font"));
        assertThrows(NullPointerException.class, () -> p.getFont("my color"));
    }

}

package junit;

import org.junit.jupiter.api.*;
import util.file.editing.*;
import util.log.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallTest {

    @Test
    void testPrint() throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, true, "utf-8"));
        Call.print("argument1", "argument2");
        String output = baos.toString("utf-8").trim();
        assertEquals("junit.CallTest::testPrint(argument1, argument2)", output);
    }

    @Test
    void testLog() {
        String filename = "log.txt";
        Logger l = new Logger(filename);
        Call.log(l, "argument1", "argument2");
        l.close();
        String output = WrappedReader.readFile(filename).trim();
        assertEquals("junit.CallTest::testLog(argument1, argument2)", output);
    }

}

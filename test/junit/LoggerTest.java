package junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Logger;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggerTest {

    private static final String LINE = "One line";
    private static final String LOG_FILE = "test.log";

    @BeforeEach
    void writeLine() {
        Logger logger = new Logger(LOG_FILE);
        logger.log(LINE);
        logger.close();
    }

    @AfterEach
    void deleteLog() {
        File log = new File(LOG_FILE);
        log.delete();
    }

    @Test
    void readTheLog() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(LOG_FILE));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {
            assertEquals(reader.readLine(), LINE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

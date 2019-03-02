package junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.log.Logger;
import util.file.editing.WrappedReader;

import java.io.File;

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
        WrappedReader reader;
        reader = new WrappedReader(LOG_FILE);
        assertEquals(reader.readLine(), LINE);
    }

}

package com.bingo.file;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterTest {

    @Test
    void ensureThatPathFromTempDirISWritable(@TempDir Path path) {
        assertTrue(Files.isWritable(path), "Ensure that the Path given to you by the @TempDir annotation if writable");
    }

    @Test
    void ensureThatNonExistingFileThrowsAnException(@TempDir Path path) {
        Path file = path.resolve("content.txt");
        assertThrows(IOException.class, () -> FileWriter.appendFile(file, "Hello world!"),
                "Ensure that a appending to a file with FileWriter.appendFile which has not yet been created");
    }

    @Test
    void ensureAppendingWorks(@TempDir Path path) {
        Path file = path.resolve("content.txt");
        FileWriter.createFile(file);
        assertTrue(Files.isReadable(file));
    }

}

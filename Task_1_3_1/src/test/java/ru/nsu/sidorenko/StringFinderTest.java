package ru.nsu.sidorenko;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class StringFinderTest {

    /**
     * Вспомогательный метод для генерации временных файлов для тестов.
     *
     * @param fileName - имя файла.
     * @param content - содержимое файла.
     * @throws IOException - исключение при ошибках.
     */
    private void writeToFile(String fileName, String content) throws IOException {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(content);
        }
    }

    @Test
    void testSimpleMatch() throws IOException {
        String fileName = "test1.txt";
        writeToFile(fileName, "абракадабра");

        StringFinder fs = new StringFinder();
        fs.find(fileName, "бра");

        assertEquals(List.of(1, 8), fs.answer);
    }

    @Test
    void chinaTest() throws IOException {
        String fileName = "test2.txt";
        writeToFile(fileName, "阿贝beijing非fēig贝beijingěi得");

        StringFinder fs = new StringFinder();
        fs.find(fileName, "贝beijing");

        assertEquals(List.of(1, 14), fs.answer);
    }

    @Test
    void emojiTest() throws IOException {
        String fileName = "test3.txt";
        writeToFile(fileName, "\uD83D\uDE00gdgdhsk\uD83D\uDE00g");

        StringFinder fs = new StringFinder();
        fs.find(fileName, "\uD83D\uDE00g");

        assertEquals(List.of(0, 8), fs.answer);
    }

    @Test
    void wrongAnswer() throws IOException {
        String fileName = "test4.txt";
        writeToFile(fileName, "абракадабра");

        StringFinder fs = new StringFinder();
        fs.find(fileName, "бра");

        assertNotEquals(List.of(2, 3, 4), fs.answer);
    }

    @Test
    void bigFile() throws IOException {
        String fileName = "test5.txt";
        String content = " " + "бра".repeat(10000);

        writeToFile(fileName, content);

        StringFinder fs = new StringFinder();
        assertDoesNotThrow(() -> fs.find(fileName, content));
    }

    @Test
    void withReturn() throws IOException {
        String fileName = "test6.txt";
        String content = "абракадабр\n"
                + "a";

        writeToFile(fileName, content);
        StringFinder fs = new StringFinder();
        fs.find(fileName, "бра");

        assertEquals(List.of(1), fs.answer);
    }

    @Test
    void noMatch() throws IOException {
        String fileName = "test7.txt";
        String content = "lalalalal";

        writeToFile(fileName, content);
        StringFinder fs = new StringFinder();
        fs.find(fileName, "lflf");

        assertEquals(List.of(), fs.answer);
    }

    @Test
    void testOverlappingMatches() throws IOException {
        String fileName = "test8.txt";
        writeToFile(fileName, "абабаб");

        StringFinder fs = new StringFinder();
        fs.find(fileName, "абаб");

        assertEquals(List.of(0, 2), fs.answer);
    }


}
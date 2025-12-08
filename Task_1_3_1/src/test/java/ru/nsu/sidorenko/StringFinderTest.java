package ru.nsu.sidorenko;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class StringFinderTest {

    @Test
    void testSimpleMatch() throws IOException {
        Reader reader = new StringReader( "абракадабра");

        StringFinder fs = new StringFinder();
        fs.find(reader, "бра");

        assertEquals(List.of(1, 8), fs.getAnswer());
    }

    @Test
    void chinaTest() throws IOException {
        Reader reader = new StringReader("阿贝beijing非fēig贝beijingěi得");

        StringFinder fs = new StringFinder();
        fs.find(reader, "贝beijing");

        assertEquals(List.of(1, 14), fs.getAnswer());
    }

    @Test
    void emojiTest() throws IOException {
        Reader reader = new StringReader( "\uD83D\uDE00gdgdhsk\uD83D\uDE00g");

        StringFinder fs = new StringFinder();
        fs.find(reader, "\uD83D\uDE00g");

        assertEquals(List.of(0, 8), fs.getAnswer());
    }

    @Test
    void wrongAnswer() throws IOException {
        Reader reader = new StringReader( "абракадабра");

        StringFinder fs = new StringFinder();
        fs.find(reader, "бра");

        assertNotEquals(List.of(2, 3, 4), fs.getAnswer());
    }

    @Test
    void bigFile() throws IOException {
        String content = " " + "бра".repeat(10000);

        StringFinder fs = new StringFinder();
        assertDoesNotThrow(() -> fs.find(new StringReader(content), content));
    }

    @Test
    void withReturn() throws IOException {
        String content = "абракадабр\n"
                + "a";

        Reader reader = new StringReader(content);
        StringFinder fs = new StringFinder();
        fs.find(reader, "бра");

        assertEquals(List.of(1), fs.getAnswer());
    }

    @Test
    void noMatch() throws IOException {
        String content = "lalalalal";

        Reader reader = new StringReader(content);
        StringFinder fs = new StringFinder();
        fs.find(reader, "lflf");

        assertEquals(List.of(), fs.getAnswer());
    }

    @Test
    void testOverlappingMatches() throws IOException {
        Reader reader = new StringReader( "абабаб");

        StringFinder fs = new StringFinder();
        fs.find(reader, "абаб");

        assertEquals(List.of(0, 2), fs.getAnswer());
    }


}
package ru.nsu.sidorenko;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StringFinderTest {

    @Test
    void testTextSerialize() {
        Text text = new Text("Hello");
        assertEquals("Hello", text.serialize());
    }

    @Test
    void testBoldSerialize() {
        Bold bold = new Bold("Bold");
        assertEquals("**Bold**", bold.serialize());
    }

    @Test
    void testItalicSerialize() {
        Italic italic = new Italic("Italic");
        assertEquals("*Italic*", italic.serialize());
    }

    @Test
    void testQuoteSerialize() {
        Quote quote = new Quote(new Text("Quote"));
        assertEquals("> Quote", quote.serialize());
    }

    @Test
    void testStrikethroughSerialize() {
        Strikethrough strike = new Strikethrough("Strike");
        assertEquals("~~Strike~~", strike.serialize());
    }

    @Test
    void testInlineCodeSerialize() {
        InlineCode code = new InlineCode("code()");
        assertEquals("`code()`", code.serialize());
    }

    @Test
    void testHeadingSerialize() {
        Heading h = new Heading("Title", 3);
        assertEquals("### Title", h.serialize());
    }

    @Test
    void testHeadingInvalidLevel() {
        assertThrows(IllegalArgumentException.class, () -> new Heading("Title", 0));
        assertThrows(IllegalArgumentException.class, () -> new Heading("Title", 7));
    }

    @Test
    void testLinkSerialize() {
        Link link = new Link("Google", "https://google.com");
        assertEquals("[Google](https://google.com)", link.serialize());
    }

    @Test
    void testImageSerialize() {
        Image img = new Image("Alt", "pic.jpg");
        assertEquals("![Alt](pic.jpg)", img.serialize());
    }

    @Test
    void testTaskListItemChecked() {
        TaskListItem item = new TaskListItem("Task", true);
        assertEquals("- [x] Task", item.serialize());
    }

    @Test
    void testTaskListItemUnchecked() {
        TaskListItem item = new TaskListItem("Task", false);
        assertEquals("- [ ] Task", item.serialize());
    }

    @Test
    void testOrderedList() {
        OrderedList list = new OrderedList(Arrays.asList(
                new Text("One"),
                new Text("Two")
        ));
        assertEquals("1. One\n2. Two", list.serialize());
    }

    @Test
    void testUnorderedList() {
        UnorderedList list = new UnorderedList(Arrays.asList(
                new Text("One"),
                new Text("Two")
        ));
        assertEquals("- One\n- Two", list.serialize());
    }

    @Test
    void testCodeBlockSerialize() {
        CodeBlock block = new CodeBlock("java", "System.out.println(\"Hi\");");
        assertEquals("```java\nSystem.out.println(\"Hi\");\n```", block.serialize());
    }

    @Test
    void testSimpleTable() {
        Table table = new Table.Builder()
                .addRow("H1", "H2")
                .addRow("1", "2")
                .build();
        String serialized = table.serialize();

        assertTrue(serialized.contains("H1"));
        assertTrue(serialized.contains("H2"));
        assertTrue(serialized.contains("1"));
        assertTrue(serialized.contains("2"));
        assertTrue(serialized.contains("|"));
        assertTrue(serialized.contains("---"));
    }

    @Test
    void testTableAlignment() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("Name", "Age")
                .build();

        String serialized = table.serialize();
        assertTrue(serialized.contains(":"));
    }

    @Test
    void testEmptyBuilderThrows() {
        Table.Builder builder = new Table.Builder();
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testRowLimit() {
        Table table = new Table.Builder()
                .addRow("H1", "H2")
                .addRow("1", "2")
                .addRow("3", "4")
                .withRowLimit(2)
                .build();

        String[] lines = table.serialize().split("\n");
        assertEquals(4, lines.length);
        assertTrue(lines[2].contains("1"));
        assertTrue(lines[3].contains("2") || lines[3].contains("3"));
    }

    @Test
    void testEqualsSameClassSameContent() {
        Element t1 = new Text("Hello");
        Element t2 = new Text("Hello");
        assertEquals(t1, t2);
    }

    @Test
    void testEqualsSameClassDifferentContent() {
        Element t1 = new Text("Hello");
        Element t2 = new Text("World");
        assertNotEquals(t1, t2);
    }

    @Test
    void testHashCodeConsistency() {
        Element t1 = new Text("Hello");
        Element t2 = new Text("Hello");
        assertEquals(t1.hashCode(), t2.hashCode());
    }
}

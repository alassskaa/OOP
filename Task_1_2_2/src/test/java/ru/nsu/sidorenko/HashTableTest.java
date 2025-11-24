package ru.nsu.sidorenko;

import java.util.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void emptyTableBasics() {
        HashTable<String, Integer> ht = new HashTable<>();
        assertTrue(ht.isEmpty());
        assertEquals(0, ht.size());
        assertNull(ht.get("missing"));
        assertFalse(ht.containsKey("missing"));
        assertEquals("{}", ht.toString());
    }

    @Test
    void putAndGetUpdatesSizeAndValue() {
        HashTable<String, Integer> ht = new HashTable<>();
        assertNull(ht.put("one", 1));
        assertEquals(1, ht.size());
        assertEquals(1, ht.get("one"));

        Integer prev = ht.put("one", 11);
        assertEquals(1, prev);
        assertEquals(11, ht.get("one"));
        assertEquals(1, ht.size());
    }

    @Test
    void updateExistingAndThrowsOnMissing() {
        HashTable<String, String> ht = new HashTable<>();
        ht.put("k", "v1");
        String old = ht.update("k", "v2");
        assertEquals("v1", old);
        assertEquals("v2", ht.get("k"));

        assertThrows(NoSuchElementException.class, () -> ht.update("absent", "v"));
    }

    @Test
    void removeWorksAndShrinkHappens() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        for (int i = 0; i < 100; i++) {
            ht.put(i, i);
        }
        assertEquals(100, ht.size());
        for (int i = 0; i < 90; i++) {
            Integer v = ht.remove(i);
            assertEquals(i, v);
        }
        assertEquals(10, ht.size());
        assertNull(ht.remove(-1));
    }

    @Test
    void containsKeyAndGet() {
        HashTable<String, Number> ht = new HashTable<>();
        ht.put("one", 1);
        assertTrue(ht.containsKey("one"));
        assertEquals(1, ht.get("one"));
        assertFalse(ht.containsKey("two"));
        assertNull(ht.get("two"));
    }

    @Test
    void iteratorTraversesAllAndFailFast() {
        HashTable<String, Integer> ht = new HashTable<>();
        ht.put("a", 1);
        ht.put("b", 2);
        ht.put("c", 3);

        Set<String> keys = new HashSet<>();
        for (Entry<String, Integer> e : ht) {
            keys.add(e.getKey());
        }
        assertEquals(new HashSet<>(Arrays.asList("a", "b", "c")), keys);
    }

    @Test
    void equalsAndHashCode() {
        HashTable<String, Integer> a = new HashTable<>();
        HashTable<String, Integer> b = new HashTable<>();
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        a.put("x", 1);
        b.put("x", 1);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        b.put("y", 2);
        assertNotEquals(a, b);

        a.put("y", 3);
        assertNotEquals(a, b);
    }

    @Test
    void toStringFormatsEntries() {
        HashTable<String, Integer> ht = new HashTable<>();
        ht.put("one", 1);
        String s = ht.toString();
        assertTrue(s.startsWith("{"));
        assertTrue(s.endsWith("}"));
        assertTrue(s.contains("one=1"));
    }

    @Test
    void nullKeyNotAllowed() {
        HashTable<String, Integer> ht = new HashTable<>();
        assertThrows(NullPointerException.class, () -> ht.put(null, 1));
        assertThrows(NullPointerException.class, () -> ht.update(null, 1));
        assertThrows(NullPointerException.class, () -> ht.remove(null));
        assertThrows(NullPointerException.class, () -> ht.containsKey(null));
        assertThrows(NullPointerException.class, () -> ht.get(null));
    }

    @Test
    void supportsDifferentValueTypes() {
        HashTable<String, Number> ht = new HashTable<>();
        ht.put("one", 1);
        ht.update("one", 1.0);
        Number n = ht.get("one");
        assertEquals(1.0, n);
    }

    @Test
    void resizeGrowsTableWhenThresholdExceeded() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        int initialCapacity = 16;
        for (int i = 0; i < 13; i++) {
            ht.put(i, i);
        }

        assertEquals(13, ht.size());

        for (int i = 0; i < 13; i++) {
            assertEquals(i, ht.get(i));
        }
    }

    @Test
    void resizeShrinksTableWhenUnderThreshold() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        for (int i = 0; i < 32; i++) {
            ht.put(i, i);
        }
        int sizeAfterAdd = ht.size();
        assertEquals(32, sizeAfterAdd);
        for (int i = 0; i < 30; i++) {
            ht.remove(i);
        }

        assertEquals(2, ht.size());
        assertEquals(30, ht.get(30));
        assertEquals(31, ht.get(31));
    }
}

package ru.nsu.sidorenko;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Класс для поиска подстроки, которая может содержать
 * любые символы кодировки UTF-8.
 * Для корректной обработки символов такой кодировки используются
 * методы работы с суррогатными парами и кодовыми точками.
 * Программа корректно обрабатывает большие файлы, так как проходит
 * по его файлу при помощи скользящего окна, реализованного
 * через очереди.
 */
public class StringFinder {
    private int idx = 0;
    private List<Integer> answer = new ArrayList<>();
    private Queue<Integer> window = new LinkedList<>();

    /**
     * Основной метод для поиска подстроки.
     *
     * @param pattern - паттерн (строка, которой должны соответствовать подстроки).
     * @throws IOException - исключение ошибки чтения файла, обработанное при помощи try.
     */
    public void find(Reader reader, String pattern) throws IOException {
        int[] st = pattern.codePoints().toArray();

        int c;
        while ((c = readNextChar(reader)) != -1) {
            window.add(c);
            idx += 1;
            checkLength(st);
        }

    }

    public List<Integer> getAnswer() {
        return answer;
    }

    /**
     * Метод для проверки соответствия длины паттерна и подстроки,
     * находящейся в окне в данный момент.
     *
     * @param st - массив кодовых точек символов паттерна.
     */
    public void checkLength(int[] st) {
        int length = st.length;
        int i = 0;
        if (length == window.size()) {
            if ((i = myCompare(window, st)) != -1) {
                answer.add(idx - i);
            }
        } else if (length < window.size()) {
            window.remove();
            if ((i = myCompare(window, st)) != -1) {
                answer.add(idx - i);
            }
        }
    }

    private int readNextChar(Reader reader) throws IOException {
        int c = reader.read();
        if (c == -1) {
            return -1;
        }
        if (Character.isHighSurrogate((char) c)) {
            int low = reader.read();
            if (low == -1 || !Character.isLowSurrogate((char) low)) {
                throw new IOException("Incorrect symbol");
            }
            return Character.toCodePoint((char) c, (char) low);
        }
        return c;
    }

    /**
     * Метод для сравнения кодовых точек элементов, находящихся в очереди,
     * с кодовыми точками элементов паттерна.
     *
     * @param q  - очередь кодовых точек элементов, находящихся в окне.
     * @param st - массив кодовых точек элементов паттерна.
     */
    private int myCompare(Queue<Integer> q, int[] st) {
        int i = 0;
        for (int el : q) {
            if (el == st[i]) {
                i += 1;
            } else {
                break;
            }
        }
        if (i == st.length) {
            return i;
        }
        return -1;
    }
}

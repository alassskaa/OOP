package ru.nsu.sidorenko;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class FindString {
    private int idx = 0;
    ArrayList<Integer> answer = new ArrayList<>();
    Queue<Integer> window = new LinkedList<>();

    /**
     * Основной метод для поиска подстроки, читающий содержимое
     * из файла при помощи BufferedReader.
     *
     * @param fileName - имя файла, из которого происходит чтение.
     * @param str - паттерн (строка, которой должны соответствовать подстроки).
     * @throws IOException - исключение ошибки чтения файла, обработанное при помощи try.
     */
    public void find(String fileName, String str) throws IOException {
        int[] st = str.codePoints().toArray();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            int c;
            while ((c = reader.read()) != -1) {
                int cp = c;

                window.add(checkSurrogate(reader, cp));
                idx += 1;
                checkLength(st);
            }
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }

    }

    /**
     * Метод для проверки соответствия длины паттерна и подстроки,
     * находящейся в окне в данный момент.
     *
     * @param st - массив кодовых точек символов паттерна.
     */
    public void checkLength(int[] st) {
        int length = st.length;
        if (length == window.size()) {
            myCompare(window, st);
        } else if (length < window.size()) {
            window.remove();
            myCompare(window, st);
        }
    }

    /**
     * Метод для сравнения кодовых точек элементов, находящихся в очереди,
     * с кодовыми точками элементов паттерна.
     *
     * @param q - очередь кодовых точек элементов, находящихся в окне.
     * @param st - массив кодовых точек элементов паттерна.
     */
    public void myCompare(Queue<Integer> q, int[] st) {
        int i = 0;
        for (int el : q) {
            if (el == st[i]) {
                i += 1;
            } else {
                break;
            }
        }
        if (i == st.length) {
            answer.add(idx - i);
        }
    }

    /**
     * Метод для проверки, является ли символ суррогатной парой. Если
     * считанный байт является верхним суррогатом, то он и следующий
     * байт - суррогатная пара. Кодовая точка суррогатной пары
     * определяется как сумма кодовых точек верхнего и нижнего суррогатов.
     *
     * @param reader - файл, из которого происходит чтение.
     * @param cp - считанный байт для проверки на верхний суррогат.
     * @return вернет кодовую точку считанного символа. Если байт оказался
     * верхним суррогатом, произойдет считывание следующего байта и вернется сумма
     * их кодовых точек. Если байт не был верхним суррогатом, вернется его кодовая точка.
     * @throws IOException - исключение ошибки чтения файла.
     */
    public int checkSurrogate(BufferedReader reader, int cp) throws IOException {
        if (Character.isHighSurrogate((char)cp)) {
            int low = reader.read();
            if (low != -1 && Character.isLowSurrogate((char)low)) {
                cp = Character.toCodePoint((char)cp, (char)low);
                return cp;
            }
        }
        return cp;
    }
}

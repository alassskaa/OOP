package ru.nsu.sidorenko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Реализация текстового представления таблиц.
 * Таблицы в Markdown соответствуют следующему формату:
 * | Заголовок 1 | Заголовок 2 |
 * |-------------|-------------|
 * | Данные 1   | Данные 2    |
 * | Данные 3   | Данные 4    |
 *
 * Использует паттерн проектирования Builder для пошагового
 * создания сложных объектов.
 */
public class Table extends Element {
    public static final String ALIGN_LEFT = ":----";
    public static final String ALIGN_RIGHT = "----:";
    public static final String ALIGN_CENTER = ":---:";
    public static final String ALIGN_DEFAULT = "-----";

    private final List<List<Element>> rows;
    private final List<String> alignments;
    private final int rowLimit;

    /**
     * Конструктор класса, который инициализирует объект
     * на основе данных, собранных с помощью Builder.
     *
     * @param builder - объект Builder, содержащий строки таблицы, выравнивания
     *                столбцов и ограничение по числу строк.
     */
    private Table(Builder builder) {
        this.rows = builder.rows;
        this.alignments = builder.alignments;
        this.rowLimit = builder.rowLimit;
    }

    @Override
    public String serialize() { //вернет готовую Markdown таблицу в строковом формате

        if (rows.isEmpty()) {
            return "";
        }

        List<List<String>> serializedRows = new ArrayList<>();
        for (List<Element> row : rows) { //перевод каждого элемента таблицы в текстовое представление
            List<String> serializedRow = new ArrayList<>();
            for (Element cell : row) {
                serializedRow.add(cell.serialize());
            }
            serializedRows.add(serializedRow);
        }

        int[] columnWidths = calculateColumnWidths(serializedRows);

        StringBuilder sb = new StringBuilder();

        sb.append("|"); //заголовок
        List<String> header = serializedRows.get(0);
        for (int i = 0; i < header.size(); i++) {
            String cell = header.get(i);
            sb.append(" ").append(padRight(cell, columnWidths[i])).append(" |");
        }
        sb.append("\n");

        sb.append("|"); //разделители
        for (int i = 0; i < columnWidths.length; i++) {
            String alignment;
            if (i < alignments.size()) {
                alignment = alignments.get(i);
            } else {
                alignment = ALIGN_DEFAULT;
            }

            String separator = getSeparatorString(alignment, columnWidths[i]);
            sb.append(separator).append("|");
        }
        sb.append("\n");

        for (int r = 1; r < Math.min(serializedRows.size(), rowLimit + 1); r++) { //строки
            sb.append("|");
            List<String> row = serializedRows.get(r);
            for (int i = 0; i < row.size(); i++) {
                String cellText = row.get(i);
                String alignment;
                if (i < alignments.size()) {
                    alignment = alignments.get(i);
                } else {
                    alignment = ALIGN_DEFAULT;
                }

                String paddedCell = padCell(cellText, columnWidths[i], alignment);

                sb.append(" ").append(paddedCell).append(" |");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Метод для создания строки-разделителя для таблицы.
     * При выравнивании слева, начало строки - двоеточие,
     * выравнивание справа - двоеточие в конце строки,
     * при выравнивании по центру - двоеточие в начале и в конце.
     * При неизвестном/незаданном выравнивании - просто набор повторяющихся
     * знаков "-".
     *
     * @param align - выравнивание столбца (лево, право, центр).
     * @param width - ширина столбца.
     * @return строку разделитель.
     */
    private String getSeparatorString(String align, int width) {
        String separator = "-".repeat(width);
        return switch (align) {
            case ALIGN_LEFT -> ":" + separator.substring(1);
            case ALIGN_RIGHT -> separator.substring(0, separator.length() - 1) + ":";
            case ALIGN_CENTER -> ":" + separator.substring(1, separator.length() - 1) + ":";
            default -> separator;
        };
    }

    /**
     * Метод для определения максимальной ширины столбцов таблицы для форматирования.
     * Принимает список списков строк, уже приведенных к формату Markdown.
     * Считает ширину всех элементов таблицы, выбирает максимальную ширину,
     * встретившуюся в каждом столбце, создавая список из максимальной необходимой
     * ширины для каждого из столбцов.
     *
     * @param serializedRows - список списков строк таблицы, в котором элементы таблицы
     *                       уже приведены к Markdown формату.
     * @return список максимальных необходимых ширин для каждого столбца.
     */
    private int[] calculateColumnWidths(List<List<String>> serializedRows) {
        int numColumns = serializedRows.get(0).size();
        int[] columnWidths = new int[numColumns];

        for (List<String> row : serializedRows) {
            for (int i = 0; i < row.size(); i++) {
                columnWidths[i] = Math.max(columnWidths[i], row.get(i).length());
            }
        }

        for (int i = 0; i < numColumns; i++) {
            String align;
            if (i < alignments.size()) {
                align = alignments.get(i);
            } else {
                align = ALIGN_DEFAULT;
            }

            int minWidth = align.replace(":", "").length();

            if (columnWidths[i] < minWidth) {
                columnWidths[i] = minWidth;
            }
        }

        return columnWidths;
    }

    /**
     * Метод, который приводит строку к желаемой длине при выравнивании слева,
     * путем добавления пробелов справа. Если желаемая ширина меньше строки, то
     * строка не обрезается, просто ничего не происходит.
     *
     * @param s - строка, которую хотим привести к желаемой ширине.
     * @param n - желаемая ширина.
     * @return - новая строка желаемой ширины.
     */
    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    /**
     * Метод, который приводит строку к желаемой длине при выравнивании справа,
     * путем добавления пробелов слева. Если желаемая ширина меньше строки, то
     * строка не обрезается, просто ничего не происходит.
     *
     * @param s - строка.
     * @param n - желаемая ширина.
     * @return строка желаемой ширины.
     */
    private String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    /**
     * Метод, который приводит строку к желаемой длине при выравнивании по центру,
     * путем добавления равного количества пробелов справа и слева (если разница
     * между длиной строки и желаемой длиной нечетная, то справа добавится на 1 пробел
     * больше). Если желаемая ширина меньше строки, то строка не обрезается,
     * просто ничего не происходит.
     *
     * @param s - строка.
     * @param n - желаемая длина строки.
     * @return строку желаемой ширины.
     */
    private String padCenter(String s, int n) {
        int padding = n - s.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        return " ".repeat(leftPadding) + s + " ".repeat(rightPadding);
    }

    /**
     * Метод, реализующий выбор способа выравнивания в зависимости от параметра.
     * При выравнивании справа добавляются пробелы слева, при выравнивании по
     * центру - пробелы с обеих сторон, в остальных случаях (выравнивание слева
     * или выравнивание не указано) - пробелы справа.
     *
     * @param s - строка, которую нужно вставить в ячейку таблицы.
     * @param n - желаемая ширина ячейки.
     * @param alignment - параметр: выравнивание слева, справа или по центру.
     * @return строка нужной ширины с нужным выравниванием.
     */
    private String padCell(String s, int n, String alignment) {
        return switch (alignment) {
            case ALIGN_RIGHT -> padLeft(s, n);
            case ALIGN_CENTER -> padCenter(s, n);
            default -> padRight(s, n);
        };
    }

    /**
     * Вложенный класс Builder, который используется для реализации соответствующего
     * паттерна проектирования. Позволяет пошагово создавать объекты класса Table.
     */
    public static class Builder {
        private final List<List<Element>> rows = new ArrayList<>();
        private List<String> alignments = new ArrayList<>();
        private int rowLimit = Integer.MAX_VALUE;

        /**
         * Метод для задания выравнивания столбцов. Принимает переменное количество аргументов.
         * Если указано меньше аргументов, чем столбцов, то остальные столбцы просто будут
         * выровнены по значениям default.
         *
         * @param alignments - выравнивание для каждого из столбцов.
         * @return текущий объект Builder.
         */
        public Builder withAlignments(String... alignments) {
            this.alignments = Arrays.asList(alignments);
            return this;
        }

        /**
         * Метод для задания максимального количества строк в таблице.
         *
         * @param rowLimit - лимит количества строк.
         * @return текущий объект Builder.
         */
        public Builder withRowLimit(int rowLimit) {
            this.rowLimit = rowLimit;
            return this;
        }

        /**
         * Метод, добавляющий одну строку в таблицу. Принимает переменное количество
         * аргументов - ячейки строки.
         *
         * @param cells - ячейки добавляемой строки.
         * @return текущий объект класса Builder.
         */
        public Builder addRow(Object... cells) {
            List<Element> row = new ArrayList<>();
            for (Object cell : cells) {
                if (cell instanceof Element) {
                    row.add((Element) cell);
                } else {
                    row.add(new Text(String.valueOf(cell)));
                }
            }
            this.rows.add(row);
            return this;
        }

        /**
         * Метод, создающий объект Table из текущего объекта класса Builder.
         *
         * @return готовый объект Table (создается при помощи вызова конструктора
         * соответствующего класса).
         */
        public Table build() {
            if (rows.isEmpty()) {
                throw new IllegalStateException("Table must have at least one row.");
            }
            return new Table(this);
        }
    }
}

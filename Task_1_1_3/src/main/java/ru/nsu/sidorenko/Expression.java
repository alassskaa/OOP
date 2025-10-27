package ru.nsu.sidorenko;

import java.util.Map;

/**
 * Абстрактный класс для работы с математическими выражениями.
 */
public abstract class Expression {
    
    /**
     * Возвращает строковое представление выражения.
     * 
     * @return вернет строковое представление
     */
    public abstract String print();
    
    /**
     * Вычисляет производную данного выражения по заданной переменной.
     *
     * @param variable — переменная, по которой требуется дифференцировать
     * @return новое выражение, представляющее производную
     */
    public abstract Expression derivative(String variable);
    
    /**
     * Вычисляет выражение с заданными значениями переменных.
     * 
     * @param assignments строка с присвоениями переменных, разделёнными точкой с запятой
     * @return вычисленный результат
     */
    public abstract int eval(String assignments);
    
    /**
     * Вспомогательный метод для разбора строки с присваиваниями.
     * 
     * @param assignments строка с присваиваниями
     * @return карта с переменными и их значениями
     */
    protected Map<String, Integer> parseAssignments(String assignments) {
        Map<String, Integer> map = new java.util.HashMap<>();
        if (assignments == null || assignments.trim().isEmpty()) {
            return map;
        }
        
        String[] parts = assignments.split(";");
        for (String part : parts) {
            String trimmed = part.trim();
            if (trimmed.isEmpty()) continue;
            
            String[] assignment = trimmed.split("=");
            if (assignment.length == 2) {
                String var = assignment[0].trim();
                int value = Integer.parseInt(assignment[1].trim());
                map.put(var, value);
            }
        }
        return map;
    }
    
    /**
     * Преобразование выражения из строки. Все выражения, кроме переменных и констант, заключены в скобки.
     * 
     * @param str строковое представление выражения
     * @return разобранное выражение.
     */
    public static Expression parse(String str) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }
        
        str = str.trim();

        try {
            int value = Integer.parseInt(str);
            return new Number(value);
        } catch (NumberFormatException e) {
            // не число
        }

        if (!str.startsWith("(")) {
            return new Variable(str);
        }

        str = str.substring(1, str.length() - 1);

        int depth = 0;
        char[] chars = str.toCharArray();
        
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                depth++;
            } else if (chars[i] == ')') {
                depth--;
            } else if (depth == 0) {
                if (chars[i] == '+') {
                    String left = str.substring(0, i).trim();
                    String right = str.substring(i + 1).trim();
                    return new Add(parse(left), parse(right));
                } else if (chars[i] == '-') {
                    String left = str.substring(0, i).trim();
                    String right = str.substring(i + 1).trim();
                    return new Sub(parse(left), parse(right));
                } else if (chars[i] == '*') {
                    String left = str.substring(0, i).trim();
                    String right = str.substring(i + 1).trim();
                    return new Mul(parse(left), parse(right));
                } else if (chars[i] == '/') {
                    String left = str.substring(0, i).trim();
                    String right = str.substring(i + 1).trim();
                    return new Div(parse(left), parse(right));
                }
            }
        }
        
        throw new IllegalArgumentException("Invalid expression: " + str);
    }
}

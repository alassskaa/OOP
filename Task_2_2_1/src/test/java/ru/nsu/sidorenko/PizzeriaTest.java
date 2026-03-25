package ru.nsu.sidorenko;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

/**
 * Тест для проверки работы программы.
 */
public class PizzeriaTest {

    @Test
    void testFullPizzeriaWithRealConfig() {
        Pizzeria pizzeria = new Pizzeria();

        assertDoesNotThrow(() -> {
            pizzeria.start("config.json");
        });
    }
}
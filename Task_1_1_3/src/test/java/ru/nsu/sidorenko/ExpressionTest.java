package ru.nsu.sidorenko;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpressionTest {
    
    @Test
    void testNumber() {
        Expression e = new Number(42);
        assertEquals("42", e.toString());
        Map<String, Integer> args = new HashMap<>();
        assertEquals(42, e.eval(args));
        
        Expression derivative = e.derivative("x");
        assertEquals("0", derivative.toString());
    }
    
    @Test
    void testVariable() {
        Expression e = new Variable("x");
        assertEquals("x", e.toString());
        Map<String, Integer> args = new HashMap<>();
        args.put("x", 10);
        assertEquals(10, e.eval(args));
        
        Expression derivative = e.derivative("x");
        assertEquals("1", derivative.toString());
        
        Expression derivative2 = e.derivative("y");
        assertEquals("0", derivative2.toString());
    }
    
    @Test
    void testAdd() {
        Expression e = new Add(new Number(3), new Number(2));
        assertEquals("(3+2)", e.toString());
        Map<String, Integer> args = new HashMap<>();
        assertEquals(5, e.eval(args));
    }
    
    @Test
    void testExampleFromTask() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(3+(2*x))", e.toString());
        Map<String, Integer> args = new HashMap<>();
        args.put("x", 10);
        args.put("y", 13);
        assertEquals(23, e.eval(args));

        Expression de = e.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", de.toString());
    }
    
    @Test
    void testMul() {
        Expression e = new Mul(new Number(5), new Variable("x"));
        assertEquals("(5*x)", e.toString());
        Map<String, Integer> args = new HashMap<>();
        args.put("x", 10);
        assertEquals(50, e.eval(args));
        
        Expression de = e.derivative("x");
        assertEquals("((0*x)+(5*1))", de.toString());
    }
    
    @Test
    void testSub() {
        Expression e = new Sub(new Number(10), new Number(3));
        assertEquals("(10-3)", e.toString());
        Map<String, Integer> args = new HashMap<>();
        assertEquals(7, e.eval(args));
        
        Expression de = e.derivative("x");
        assertEquals("(0-0)", de.toString());
    }
    
    @Test
    void testDiv() {
        Expression e = new Div(new Number(20), new Number(4));
        assertEquals("(20/4)", e.toString());
        Map<String, Integer> args = new HashMap<>();
        assertEquals(5, e.eval(args));
    }
    
    @Test
    void testParseNumber() {
        Expression e = ExpressionParser.parse("42");
        assertTrue(e instanceof Number);
        assertEquals("42", e.toString());
        Map<String, Integer> args = new HashMap<>();
        assertEquals(42, e.eval(args));
    }
    
    @Test
    void testParseVariable() {
        Expression e = ExpressionParser.parse("x");
        assertTrue(e instanceof Variable);
        assertEquals("x", e.toString());
    }
    
    @Test
    void testParseAdd() {
        Expression e = ExpressionParser.parse("(3+5)");
        assertEquals("(3+5)", e.toString());
        Map<String, Integer> args = new HashMap<>();
        assertEquals(8, e.eval(args));
    }
    
    @Test
    void testParseComplex() {
        Expression e = ExpressionParser.parse("(3+(2*x))");
        assertEquals("(3+(2*x))", e.toString());
        Map<String, Integer> args = new HashMap<>();
        args.put("x", 10);
        assertEquals(23, e.eval(args));

        Expression de = e.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", de.toString());
    }
    
    @Test
    void testParseMul() {
        Expression e = ExpressionParser.parse("(5*x)");
        assertEquals("(5*x)", e.toString());
        Map<String, Integer> args = new HashMap<>();
        args.put("x", 10);
        assertEquals(50, e.eval(args));
    }
    
    @Test
    void testParseSub() {
        Expression e = ExpressionParser.parse("(10-3)");
        assertEquals("(10-3)", e.toString());
        Map<String, Integer> args = new HashMap<>();
        assertEquals(7, e.eval(args));
    }
    
    @Test
    void testParseDiv() {
        Expression e = ExpressionParser.parse("(20/4)");
        assertEquals("(20/4)", e.toString());
        Map<String, Integer> args = new HashMap<>();
        assertEquals(5, e.eval(args));
    }
    
    @Test
    void testComplexExpression() {
        Expression e = new Add(
            new Mul(new Variable("x"), new Variable("y")),
            new Sub(new Variable("z"), new Number(10))
        );
        assertEquals("((x*y)+(z-10))", e.toString());
        Map<String, Integer> args = new HashMap<>();
        args.put("x", 5);
        args.put("y", 2);
        args.put("z", 1011);
        assertEquals(1011, e.eval(args));
    }
    
    @Test
    void testEmptyAssignments() {
        Expression e = new Number(5);
        Map<String, Integer> args = new HashMap<>();
        assertEquals(5, e.eval(args));
        assertEquals(5, e.eval(args));
    }

    @Test
    void testDivDerivative() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        Expression de = e.derivative("x");
        assertEquals("(((1*y)-(x*0))/(y*y))", de.toString());
    }

    @Test
    void testDivByZero() {
        Expression e = new Div(new Number(10), new Number(0));
        Map<String, Integer> args = new HashMap<>();
        assertThrows(ArithmeticException.class, () -> e.eval(args));
    }

    @Test
    void testParseAssignments() {
        Map<String, Integer> result = ExpressionParser.parseAssignments("x = 10; y = 20");
        assertEquals(2, result.size());
        assertEquals(10, result.get("x"));
        assertEquals(20, result.get("y"));
        
        Map<String, Integer> empty = ExpressionParser.parseAssignments("");
        assertTrue(empty.isEmpty());
        
        Map<String, Integer> nullResult = ExpressionParser.parseAssignments(null);
        assertTrue(nullResult.isEmpty());
    }
}


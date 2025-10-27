package ru.nsu.sidorenko;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {
    
    @Test
    void testNumber() {
        Expression e = new Number(42);
        assertEquals("42", e.print());
        assertEquals(42, e.eval(""));
        
        Expression derivative = e.derivative("x");
        assertEquals("0", derivative.print());
    }
    
    @Test
    void testVariable() {
        Expression e = new Variable("x");
        assertEquals("x", e.print());
        assertEquals(10, e.eval("x = 10"));
        
        Expression derivative = e.derivative("x");
        assertEquals("1", derivative.print());
        
        Expression derivative2 = e.derivative("y");
        assertEquals("0", derivative2.print());
    }
    
    @Test
    void testAdd() {
        Expression e = new Add(new Number(3), new Number(2));
        assertEquals("(3+2)", e.print());
        assertEquals(5, e.eval(""));
    }
    
    @Test
    void testExampleFromTask() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(3+(2*x))", e.print());
        assertEquals(23, e.eval("x = 10; y = 13"));

        Expression de = e.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", de.print());
    }
    
    @Test
    void testMul() {
        Expression e = new Mul(new Number(5), new Variable("x"));
        assertEquals("(5*x)", e.print());
        assertEquals(50, e.eval("x = 10"));
        
        Expression de = e.derivative("x");
        assertEquals("((0*x)+(5*1))", de.print());
    }
    
    @Test
    void testSub() {
        Expression e = new Sub(new Number(10), new Number(3));
        assertEquals("(10-3)", e.print());
        assertEquals(7, e.eval(""));
        
        Expression de = e.derivative("x");
        assertEquals("(0-0)", de.print());
    }
    
    @Test
    void testDiv() {
        Expression e = new Div(new Number(20), new Number(4));
        assertEquals("(20/4)", e.print());
        assertEquals(5, e.eval(""));
    }
    
    @Test
    void testParseNumber() {
        Expression e = Expression.parse("42");
        assertTrue(e instanceof Number);
        assertEquals("42", e.print());
        assertEquals(42, e.eval(""));
    }
    
    @Test
    void testParseVariable() {
        Expression e = Expression.parse("x");
        assertTrue(e instanceof Variable);
        assertEquals("x", e.print());
    }
    
    @Test
    void testParseAdd() {
        Expression e = Expression.parse("(3+5)");
        assertEquals("(3+5)", e.print());
        assertEquals(8, e.eval(""));
    }
    
    @Test
    void testParseComplex() {
        Expression e = Expression.parse("(3+(2*x))");
        assertEquals("(3+(2*x))", e.print());
        assertEquals(23, e.eval("x = 10"));

        Expression de = e.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", de.print());
    }
    
    @Test
    void testParseMul() {
        Expression e = Expression.parse("(5*x)");
        assertEquals("(5*x)", e.print());
        assertEquals(50, e.eval("x = 10"));
    }
    
    @Test
    void testParseSub() {
        Expression e = Expression.parse("(10-3)");
        assertEquals("(10-3)", e.print());
        assertEquals(7, e.eval(""));
    }
    
    @Test
    void testParseDiv() {
        Expression e = Expression.parse("(20/4)");
        assertEquals("(20/4)", e.print());
        assertEquals(5, e.eval(""));
    }
    
    @Test
    void testComplexExpression() {
        Expression e = new Add(
            new Mul(new Variable("x"), new Variable("y")),
            new Sub(new Variable("z"), new Number(10))
        );
        assertEquals("((x*y)+(z-10))", e.print());
        assertEquals(1011, e.eval("x = 5; y = 2; z = 1011"));
    }
    
    @Test
    void testEmptyAssignments() {
        Expression e = new Number(5);
        assertEquals(5, e.eval(""));
        assertEquals(5, e.eval(null));
    }
}


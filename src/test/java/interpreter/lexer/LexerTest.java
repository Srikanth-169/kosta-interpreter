package interpreter.lexer;

import com.github.konstantinevashalomidze.interpreter.lexer.Lexer;
import com.github.konstantinevashalomidze.interpreter.token.Token;
import com.github.konstantinevashalomidze.interpreter.token.types.*;
import com.github.konstantinevashalomidze.interpreter.token.types.Integer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void testBasicTokenization() throws Exception {
        String input = "=+(){},;";
        Lexer lexer = new Lexer(input);

        Token[] expectedTokens = {
                new Assign(),
                new Plus(),
                new Lp(),
                new Rp(),
                new Lb(),
                new Rb(),
                new Comma(),
                new Semicolon(),
                new Eof()
        };

        for (Token expected : expectedTokens) {
            Token actual = lexer.readAndMoveOnNextToken();
            assertEquals(expected.getClass(), actual.getClass());
        }
    }

    @Test
    void testComplexProgram() throws Exception {
        String input = """
            var five = 5;
            var ten = 10;
            var add = fn(x, y) {
                x + y;
            };
            var result = add(five, ten);
            !-/*5;
            5 < 10 > 5;
            """;

        Lexer lexer = new Lexer(input);
        Class<?>[] expectedTokenTypes = {
                Variable.class, Identifier.class, Assign.class, Integer.class, Semicolon.class,
                Variable.class, Identifier.class, Assign.class, Integer.class, Semicolon.class,
                Variable.class, Identifier.class, Assign.class, Function.class, Lp.class,
                Identifier.class, Comma.class, Identifier.class, Rp.class, Lb.class,
                Identifier.class, Plus.class, Identifier.class, Semicolon.class,
                Rb.class, Semicolon.class,
                Variable.class, Identifier.class, Assign.class, Identifier.class,
                Lp.class, Identifier.class, Comma.class, Identifier.class, Rp.class, Semicolon.class,
                Bang.class, MinusPrefix.class, Slash.class, Asterisk.class, Integer.class, Semicolon.class,
                Integer.class, Lt.class, Integer.class, Gt.class, Integer.class, Semicolon.class,
                Eof.class
        };

        for (Class<?> expected : expectedTokenTypes) {
            Token actual = lexer.readAndMoveOnNextToken();
            assertEquals(expected, actual.getClass());
        }
    }

    @Test
    void testOperators() throws Exception {

        String input = "=== != && ||";
        Lexer lexer = new Lexer(input);

        Class<?>[] expectedTokenTypes = {
                Eq.class,
                Assign.class,
                NotEq.class,
                And.class,
                And.class,
                Or.class,
                Or.class,
                Eof.class
        };

        for (Class<?> expected : expectedTokenTypes) {
            Token actual = lexer.readAndMoveOnNextToken();
            assertEquals(expected, actual.getClass());
        }
    }

    @Test
    void testMinusPrefixAndInfix() throws Exception {
        String input = "-5 + 10 - 15";
        Lexer lexer = new Lexer(input);

        Class<?>[] expectedTokenTypes = {
                MinusPrefix.class,
                Integer.class,
                Plus.class,
                Integer.class,
                MinusInfix.class,
                Integer.class,
                Eof.class
        };

        for (Class<?> expected : expectedTokenTypes) {
            Token actual = lexer.readAndMoveOnNextToken();
            assertEquals(expected, actual.getClass());
        }
    }

    @Test
    void testIllegalCharacters() throws Exception {
        String input = "@#$";
        Lexer lexer = new Lexer(input);

        Token token = lexer.readAndMoveOnNextToken();
        assertInstanceOf(Illegal.class, token);
    }

    @Test
    void testIdentifierLiterals() throws Exception {
        String input = "variable x y123";
        Lexer lexer = new Lexer(input);

        Token first = lexer.readAndMoveOnNextToken();
        assertInstanceOf(Identifier.class, first);
        assertEquals("variable", first.literal());

        Token second = lexer.readAndMoveOnNextToken();
        assertInstanceOf(Identifier.class, second);
        assertEquals("x", second.literal());

        Token third = lexer.readAndMoveOnNextToken();
        assertInstanceOf(Identifier.class, third);
        assertEquals("y", third.literal());
    }
}
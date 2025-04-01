package interpreter.ast.expression;

import com.github.konstantinevashalomidze.interpreter.ast.expression.BooleanLiteral;
import com.github.konstantinevashalomidze.interpreter.token.Token;
import com.github.konstantinevashalomidze.interpreter.token.types.False;
import com.github.konstantinevashalomidze.interpreter.token.types.True;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanLiteralTest {

    @Test
    public void testConstructor() {
        Token trueToken = new True();
        BooleanLiteral trueLiteral = new BooleanLiteral(trueToken);

        assertEquals(trueToken, trueLiteral.getToken());
    }

    @Test
    public void testLiteral() {
        Token trueToken = new True();
        BooleanLiteral trueLiteral = new BooleanLiteral(trueToken);

        assertEquals("true", trueLiteral.literal());
    }

    @Test
    public void testGetValue() {
        Token trueToken = new True();
        BooleanLiteral trueLiteral = new BooleanLiteral(trueToken);

        Token falseToken = new False();
        BooleanLiteral falseLiteral = new BooleanLiteral(falseToken);

        assertTrue(trueLiteral.getValue());
        assertFalse(falseLiteral.getValue());
    }

    @Test
    public void testSetToken() {
        Token trueToken = new True();
        BooleanLiteral booleanLiteral = new BooleanLiteral(trueToken);

        Token falseToken = new False();
        booleanLiteral.setToken(falseToken);

        assertEquals(falseToken, booleanLiteral.getToken());
        assertEquals("false", booleanLiteral.literal());
    }

}
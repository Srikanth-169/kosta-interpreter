package interpreter.ast.expression;

import interperter.ast.expression.IntegerLiteral;
import interperter.token.Token;
import interperter.token.types.Integer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntegerLiteralTest {

    @Test
    public void testConstructor() {
        Token token = new Integer().setLiteral("5");
        IntegerLiteral integerLiteral = new IntegerLiteral(token);

        assertEquals(token, integerLiteral.getToken());
    }

    @Test
    public void testLiteral() {
        Token token = new Integer().setLiteral("5");
        IntegerLiteral integerLiteral = new IntegerLiteral(token);

        assertEquals("5", integerLiteral.literal());
    }

    @Test
    public void testGetValue() {
        Token token = new Integer().setLiteral("5");
        IntegerLiteral integerLiteral = new IntegerLiteral(token);

        assertEquals(5, integerLiteral.getValue());
    }

    @Test
    public void testSetToken() {
        Token token = new Integer().setLiteral("5");
        IntegerLiteral integerLiteral = new IntegerLiteral(token);

        Token newToken = new Integer().setLiteral("10");
        integerLiteral.setToken(newToken);

        assertEquals(newToken, integerLiteral.getToken());
        assertEquals(10, integerLiteral.getValue());
    }

    @Test
    public void testGetValueWithNegativeNumber() {
        Token token = new Integer().setLiteral("-5");
        IntegerLiteral integerLiteral = new IntegerLiteral(token);

        assertEquals(-5, integerLiteral.getValue());
    }
}
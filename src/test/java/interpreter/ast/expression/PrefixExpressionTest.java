package interpreter.ast.expression;

import interperter.ast.expression.BooleanLiteral;
import interperter.ast.expression.Expression;
import interperter.ast.expression.PrefixExpression;
import interperter.token.Token;
import interperter.token.types.Bang;
import interperter.token.types.MinusPrefix;
import interperter.token.types.True;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrefixExpressionTest {

    @Test
    public void testConstructor() {
        Token token = new Bang();
        PrefixExpression prefixExpression = new PrefixExpression(token);

        assertEquals(token, prefixExpression.getToken());
        assertNull(prefixExpression.getRight());
    }

    @Test
    public void testLiteral() {
        Token token = new Bang();
        PrefixExpression prefixExpression = new PrefixExpression(token);

        assertEquals("!", prefixExpression.literal());
    }

    @Test
    public void testGetOperator() {
        Token token = new Bang();
        PrefixExpression prefixExpression = new PrefixExpression(token);

        assertEquals("!", prefixExpression.getOperator());
    }

    @Test
    public void testSetToken() {
        Token token = new Bang();
        PrefixExpression prefixExpression = new PrefixExpression(token);

        Token newToken = new MinusPrefix();
        prefixExpression.setToken(newToken);

        assertEquals(newToken, prefixExpression.getToken());
        assertEquals("-", prefixExpression.getOperator());
    }

    @Test
    public void testSetRight() {
        Token token = new Bang();
        PrefixExpression prefixExpression = new PrefixExpression(token);

        Expression right = new BooleanLiteral(new True());
        prefixExpression.setRight(right);

        assertEquals(right, prefixExpression.getRight());
    }




}
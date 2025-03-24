package interpreter.ast.expression;

import com.github.konstantinevashalomidze.interpreter.ast.expression.BooleanLiteral;
import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interpreter.ast.expression.PrefixExpression;
import com.github.konstantinevashalomidze.interpreter.token.Token;
import com.github.konstantinevashalomidze.interpreter.token.types.Bang;
import com.github.konstantinevashalomidze.interpreter.token.types.MinusPrefix;
import com.github.konstantinevashalomidze.interpreter.token.types.True;
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
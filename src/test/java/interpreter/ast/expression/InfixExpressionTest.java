package interpreter.ast.expression;

import interperter.ast.expression.Expression;
import interperter.ast.expression.InfixExpression;
import interperter.ast.expression.IntegerLiteral;
import interperter.token.Token;
import interperter.token.types.Integer;
import interperter.token.types.MinusInfix;
import interperter.token.types.Plus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InfixExpressionTest {

    @Test
    public void testConstructorWithToken() {
        Token token = new Plus();
        InfixExpression infixExpression = new InfixExpression(token);

        assertEquals(token, infixExpression.getToken());
        assertNull(infixExpression.getLeft());
        assertNull(infixExpression.getRight());
    }

    @Test
    public void testConstructorWithTokenAndLeft() {
        Token token = new Plus();
        Expression left = new IntegerLiteral(new Integer().setLiteral("5"));

        InfixExpression infixExpression = new InfixExpression(token, left);

        assertEquals(token, infixExpression.getToken());
        assertEquals(left, infixExpression.getLeft());
        assertNull(infixExpression.getRight());
    }

    @Test
    public void testLiteral() {
        Token token = new Plus();
        InfixExpression infixExpression = new InfixExpression(token);

        assertEquals("+", infixExpression.literal());
    }

    @Test
    public void testGetOperator() {
        Token token = new Plus();
        InfixExpression infixExpression = new InfixExpression(token);

        assertEquals("+", infixExpression.getOperator());
    }

    @Test
    public void testSetToken() {
        Token token = new Plus();
        InfixExpression infixExpression = new InfixExpression(token);

        Token newToken = new MinusInfix();
        infixExpression.setToken(newToken);

        assertEquals(newToken, infixExpression.getToken());
        assertEquals("-", infixExpression.getOperator());
    }

    @Test
    public void testSetRight() {
        Token token = new Plus();
        InfixExpression infixExpression = new InfixExpression(token);

        Expression right = new IntegerLiteral(new Integer().setLiteral("10"));
        infixExpression.setRight(right);

        assertEquals(right, infixExpression.getRight());
    }

    @Test
    public void testSetLeft() {
        Token token = new Plus();
        InfixExpression infixExpression = new InfixExpression(token);

        Expression left = new IntegerLiteral(new Integer().setLiteral("5"));
        infixExpression.setLeft(left);

        assertEquals(left, infixExpression.getLeft());
    }

}
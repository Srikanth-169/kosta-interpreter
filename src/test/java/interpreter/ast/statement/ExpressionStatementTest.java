package interpreter.ast.statement;

import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interpreter.ast.statement.ExpressionStatement;
import com.github.konstantinevashalomidze.interpreter.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ExpressionStatementTest {

    private Token mockToken;
    private Expression mockExpression;

    @BeforeEach
    public void setUp() {
        mockToken = mock(Token.class);
        mockExpression = mock(Expression.class);
    }

    @Test
    public void testConstructorWithTokenAndExpression() {
        ExpressionStatement statement = new ExpressionStatement(mockToken, mockExpression);

        assertEquals(mockToken, statement.getToken());
        assertEquals(mockExpression, statement.getExpression());
    }

    @Test
    public void testConstructorWithToken() {
        ExpressionStatement statement = new ExpressionStatement(mockToken);

        assertEquals(mockToken, statement.getToken());
        assertNull(statement.getExpression());
    }

    @Test
    public void testDefaultConstructor() {
        ExpressionStatement statement = new ExpressionStatement();

        assertNull(statement.getToken());
        assertNull(statement.getExpression());
    }

    @Test
    public void testLiteral() {
        when(mockToken.literal()).thenReturn("test");

        ExpressionStatement statement = new ExpressionStatement(mockToken);

        assertEquals("test", statement.literal());
    }


    @Test
    public void testSettersAndGetters() {
        ExpressionStatement statement = new ExpressionStatement();

        statement.setToken(mockToken);
        statement.setExpression(mockExpression);

        assertEquals(mockToken, statement.getToken());
        assertEquals(mockExpression, statement.getExpression());
    }
}
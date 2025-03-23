package interpreter.ast.statement;

import interperter.ast.expression.Expression;
import interperter.ast.statement.ReturnStatement;
import interperter.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ReturnStatementTest
{

    private Token mockToken;
    private Expression mockExpression;

    @BeforeEach
    public void setUp()
    {
        mockToken = mock(Token.class);
        mockExpression = mock(Expression.class);
    }

    @Test
    public void testConstructorWithTokenAndValue()
    {
        ReturnStatement statement = new ReturnStatement(mockToken, mockExpression);

        assertEquals(mockToken, statement.getToken());
        assertEquals(mockExpression, statement.getValue());
    }

    @Test
    public void testConstructorWithToken()
    {
        ReturnStatement statement = new ReturnStatement(mockToken);

        assertEquals(mockToken, statement.getToken());
        assertNull(statement.getValue());
    }

    @Test
    public void testDefaultConstructor()
    {
        ReturnStatement statement = new ReturnStatement();

        assertNull(statement.getToken());
        assertNull(statement.getValue());
    }

    @Test
    public void testLiteral()
    {
        when(mockToken.literal()).thenReturn("return");

        ReturnStatement statement = new ReturnStatement(mockToken);

        assertEquals("return", statement.literal());
    }

    @Test
    public void testToStringWithoutValue()
    {
        when(mockToken.literal()).thenReturn("return");

        ReturnStatement statement = new ReturnStatement(mockToken);

        assertEquals("return ;", statement.toString());
    }

    @Test
    public void testSettersAndGetters()
    {
        ReturnStatement statement = new ReturnStatement();

        statement.setToken(mockToken);
        statement.setValue(mockExpression);

        assertEquals(mockToken, statement.getToken());
        assertEquals(mockExpression, statement.getValue());
    }
}
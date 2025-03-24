package interpreter.ast.statement;

import com.github.konstantinevashalomidze.interperter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interperter.ast.expression.Identifier;
import com.github.konstantinevashalomidze.interperter.ast.statement.VarStatement;
import com.github.konstantinevashalomidze.interperter.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class VarStatementTest {

    private Token mockToken;
    private Expression mockExpression;

    @BeforeEach
    public void setUp() {
        mockToken = mock(Token.class);
        // Create a real Identifier instance instead of mocking
        mockExpression = mock(Expression.class);
    }

    @Test
    public void testConstructorWithAllParameters() {
        Identifier identifier = new Identifier(new com.github.konstantinevashalomidze.interperter.token.types.Identifier());
        VarStatement statement = new VarStatement(mockToken, identifier, mockExpression);

        assertEquals(mockToken, statement.getToken());
        assertEquals(identifier, statement.getName());
        assertEquals(mockExpression, statement.getValue());
    }

    @Test
    public void testConstructorWithToken() {
        VarStatement statement = new VarStatement(mockToken);

        assertEquals(mockToken, statement.getToken());
        assertNull(statement.getName());
        assertNull(statement.getValue());
    }

    @Test
    public void testDefaultConstructor() {
        VarStatement statement = new VarStatement();

        assertNull(statement.getToken());
        assertNull(statement.getName());
        assertNull(statement.getValue());
    }

    @Test
    public void testLiteral() {
        when(mockToken.literal()).thenReturn("var");

        VarStatement statement = new VarStatement(mockToken);

        assertEquals("var", statement.literal());
    }



    @Test
    public void testSettersAndGetters() {
        VarStatement statement = new VarStatement();
        Identifier identifier = new Identifier(new com.github.konstantinevashalomidze.interperter.token.types.Identifier().setLiteral("testVar"));


        statement.setToken(mockToken);
        statement.setName(identifier);
        statement.setValue(mockExpression);

        assertEquals(mockToken, statement.getToken());
        assertEquals(identifier, statement.getName());
        assertEquals(mockExpression, statement.getValue());
    }
}
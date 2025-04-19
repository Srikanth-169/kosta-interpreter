package interpreter.ast.statement;

import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interpreter.ast.statement.ExpressionStatement;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpressionStatementTest {

    private Token token;
    private Expression expression;

    @BeforeEach
    void setUp() {
        token = mock(Token.class);
        expression = mock(Expression.class);
    }

    @AfterEach
    void tearDown() {
        token = null;
        expression = null;
    }

    @Test
    void shouldCreateExpressionStatementWithTokenAndExpression() {
        // Act
        ExpressionStatement expressionStatement = new ExpressionStatement(token, expression);

        // Assert
        assertEquals(token, expressionStatement.getToken());
        assertEquals(expression, expressionStatement.getExpression());
    }

    @Test
    void shouldCreateExpressionStatementWithTokenOnly() {
        // Act
        ExpressionStatement expressionStatement = new ExpressionStatement(token);

        // Assert
        assertEquals(token, expressionStatement.getToken());
        assertNull(expressionStatement.getExpression());
    }

    @Test
    void shouldCreateDefaultReturnStatement() {
        // Act
        ExpressionStatement expressionStatement = new ExpressionStatement();

        // Assert
        assertNull(expressionStatement.getToken());
        assertNull(expressionStatement.getExpression());
    }

    @Test
    void shouldSetTokenSuccessfully() {
        // Arrange
        ExpressionStatement expressionStatement = new ExpressionStatement();

        // Act
        expressionStatement.setToken(token);

        // Assert
        assertEquals(token, expressionStatement.getToken());
    }

    @Test
    void shouldSetValueSuccessfully() {
        // Arrange
        ExpressionStatement expressionStatement = new ExpressionStatement();

        // Act
        expressionStatement.setExpression(expression);

        // Assert
        assertEquals(expression, expressionStatement.getExpression());
    }

    @Test
    void shouldReturnLiteralFromToken() {
        // Act
        when(token.literal()).thenReturn("TokenLiteralMock");
        ExpressionStatement expressionStatement = new ExpressionStatement(token);

        // Assert
        assertEquals("TokenLiteralMock", expressionStatement.literal());
    }

    @Test
    void shouldReturnStringRepresentationWithTokenAndExpressionNextLine() {
        // Arrange
        when(token.literal()).thenReturn("TokenLiteralMock");
        when(expression.toString()).thenReturn("MockExpression\n");
        ExpressionStatement expressionStatement = new ExpressionStatement(token, expression);

        // Act
        String result = expressionStatement.toString();

        // Assert
        String expected = "ExpressionStatement (TokenLiteralMock)\n  |- MockExpression\n  |  \n";
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnStringRepresentationWithTokenAndExpression() {
        // Arrange
        when(token.literal()).thenReturn("TokenLiteralMock");
        when(expression.toString()).thenReturn("MockExpression");
        ExpressionStatement expressionStatement = new ExpressionStatement(token, expression);

        // Act
        String result = expressionStatement.toString();

        // Assert
        String expected = "ExpressionStatement (TokenLiteralMock)\n  |- MockExpression\n";
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnStringRepresentationWithTokenOnly() {
        // Act
        when(token.literal()).thenReturn("TokenLiteralMock");
        ExpressionStatement expressionStatement = new ExpressionStatement(token);

        // Assert
        String expected = "ExpressionStatement (TokenLiteralMock)\n";
        assertEquals(expected, expressionStatement.toString());
    }

    @Test
    void shouldReturnStringRepresentationWithNoTokenOrExpression() {
        // Act
        ExpressionStatement expressionStatement = new ExpressionStatement();

        // Assert
        String expected = "ExpressionStatement\n";
        assertEquals(expected, expressionStatement.toString());
    }

    @Test
    void shouldReturnStringRepresentationWithExpressionNextLineOnly() {
        // Arrange
        when(expression.toString()).thenReturn("MockExpression\n");

        ExpressionStatement expressionStatement = new ExpressionStatement();
        expressionStatement.setExpression(expression);

        // Act
        String result = expressionStatement.toString();

        // Assert
        String expected = "ExpressionStatement\n  |- MockExpression\n  |  \n";
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnStringRepresentationWithExpressionOnly() {
        // Arrange
        when(expression.toString()).thenReturn("MockExpression");

        ExpressionStatement expressionStatement = new ExpressionStatement();
        expressionStatement.setExpression(expression);

        // Act
        String result = expressionStatement.toString();

        // Assert
        String expected = "ExpressionStatement\n  |- MockExpression\n";
        assertEquals(expected, result);
    }
}
package interpreter.ast.statement;

import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interpreter.ast.statement.VarStatement;
import com.github.konstantinevashalomidze.interpreter.token.Token;
import com.github.konstantinevashalomidze.interpreter.ast.expression.Identifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VarStatementTest {
    private Token token;
    private Identifier name;
    private Expression value;

    @BeforeEach
    void setUp(){
        token = mock(Token.class);
        name = new Identifier(mock(Token.class));
        value = mock(Expression.class);
    }

    @AfterEach
    void tearDown(){
        token = null;
        name = null;
        value = null;
    }

    @Test
    void shouldCreateVarStatementWithTokenAndIdentifierAndValue(){
        //Arrange
        VarStatement varStatement = new VarStatement(token, name, value);

        //Act

        //Assert
        assertEquals(token, varStatement.getToken());
        assertEquals(name, varStatement.getName());
        assertEquals(value, varStatement.getValue());
    }

    @Test
    void shouldCreateVarStatementWithTokenOnly(){
        //Arrange
        VarStatement varStatement = new VarStatement(token);

        //Act

        //Assert
        assertEquals(token, varStatement.getToken());
        assertNull(varStatement.getName());
        assertNull(varStatement.getValue());
    }

    @Test
    void shouldCreateDefaultVarStatementWith() {
        //Arrange
        VarStatement varStatement = new VarStatement();

        //Act

        //Assert
        assertNull(varStatement.getToken());
        assertNull(varStatement.getName());
        assertNull(varStatement.getValue());
    }

    @Test
    void shouldReturnLiteralFromToken(){
        //Arrange
        when(token.literal()).thenReturn("TokenLiteralMock");
        VarStatement statement = new VarStatement(token);

        //Act

        //Assert
        assertEquals("TokenLiteralMock", statement.literal());
    }

    @Test
    void shouldSetTokenSuccessfully(){
        //Arrange
        VarStatement statement = new VarStatement();

        //Act
        statement.setToken(token);

        //Assert
        assertEquals(token, statement.getToken());
    }

    @Test
    void shouldSetValueSuccessfully(){
        //Arrange
        VarStatement statement = new VarStatement();

        //Act
        statement.setValue(value);

        //Assert
        assertEquals(value, statement.getValue());
    }

    @Test
    void shouldSetNameSuccessfully(){
        //Arrange
        VarStatement statement = new VarStatement();

        //Act
        statement.setName(name);

        //Assert
        assertEquals(name, statement.getName());
    }

    @Test
    void shouldReturnStringRepresentationWithValueAndTokenAndName(){
        //Arrange
        when(token.literal()).thenReturn("TokenLiteralMock");
        when(value.toString()).thenReturn("ExpressionMock");
        when(name.toString()).thenReturn("IdentifierMock");
        VarStatement statement = new VarStatement(token, name, value);

        //Act
        String actual = statement.toString();

        //Assert
        String expected = "VarStatement (TokenLiteralMock)\n  |- Name: Identifier (IdentifierMock)\n  |- Value: ExpressionMock\n";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnStringRepresentationWithValueAndToken(){
        //Arrange
        when(token.literal()).thenReturn("TokenLiteralMock");
        when(value.toString()).thenReturn("ExpressionMock");
        VarStatement statement = new VarStatement();

        //Act
        statement.setToken(token);
        statement.setValue(value);
        String actual = statement.toString();

        //Assert
        String expected = "VarStatement (TokenLiteralMock)\n  |- Value: ExpressionMock\n";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnStringRepresentationWithValueAndName(){
        //Arrange
        when(value.toString()).thenReturn("ExpressionMock");
        when(name.toString()).thenReturn("IdentifierMock");
        VarStatement statement = new VarStatement();

        //Act
        statement.setValue(value);
        statement.setName(name);
        String actual = statement.toString();

        //Assert
        String expected = "VarStatement\n  |- Name: Identifier (IdentifierMock)\n  |- Value: ExpressionMock\n";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnStringRepresentationTokenAndName(){
        //Arrange
        when(token.literal()).thenReturn("TokenLiteralMock");
        when(name.toString()).thenReturn("IdentifierMock");
        VarStatement statement = new VarStatement();

        //Act
        statement.setName(name);
        statement.setToken(token);
        String actual = statement.toString();

        //Assert
        String expected = "VarStatement (TokenLiteralMock)\n  |- Name: Identifier (IdentifierMock)\n";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnStringRepresentationWithTokenOnly(){
        //Arrange
        when(token.literal()).thenReturn("TokenLiteralMock");
        VarStatement statement = new VarStatement(token);

        //Act
        String actual = statement.toString();

        //Assert
        String expected = "VarStatement (TokenLiteralMock)\n";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnStringRepresentationWithValueOnly(){
        //Arrange
        when(token.literal()).thenReturn("ExpressionMock");
        VarStatement statement = new VarStatement(token);

        //Act
        String actual = statement.toString();

        //Assert
        String expected = "VarStatement (ExpressionMock)\n";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnStringRepresentationWithNameOnly(){
        //Arrange
        when(token.literal()).thenReturn("IdentifierMock");
        VarStatement statement = new VarStatement(token);

        //Act
        String actual = statement.toString();

        //Assert
        String expected = "VarStatement (IdentifierMock)\n";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnStringRepresentationWithNoTokenOrValueOrName(){
        //Arrange
        VarStatement statement = new VarStatement();

        //Act
        String actual = statement.toString();

        //Assert
        String expected = "VarStatement\n";
        assertEquals(expected, actual);

    }
}
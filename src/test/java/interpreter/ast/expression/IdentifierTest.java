package interpreter.ast.expression;

import com.github.konstantinevashalomidze.interperter.ast.expression.Identifier;
import com.github.konstantinevashalomidze.interperter.token.Token;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IdentifierTest {

    @Test
    public void testConstructor() {
        Token token = new com.github.konstantinevashalomidze.interperter.token.types.Identifier().setLiteral("variable");
        Identifier identifier = new Identifier(token);

        assertEquals(token, identifier.getToken());
    }

    @Test
    public void testDefaultConstructor() {
        Identifier identifier = new Identifier();
        assertNull(identifier.getToken());
    }

    @Test
    public void testLiteral() {
        Token token = new com.github.konstantinevashalomidze.interperter.token.types.Identifier().setLiteral("variable");
        Identifier identifier = new Identifier(token);

        assertEquals("variable", identifier.literal());
    }

    @Test
    public void testGetValue() {
        Token token = new com.github.konstantinevashalomidze.interperter.token.types.Identifier().setLiteral("variable");
        Identifier identifier = new Identifier(token);

        assertEquals("variable", identifier.getValue());
    }

    @Test
    public void testSetToken() {
        Token token = new com.github.konstantinevashalomidze.interperter.token.types.Identifier().setLiteral("variable");
        Identifier identifier = new Identifier(token);

        Token newToken = new com.github.konstantinevashalomidze.interperter.token.types.Identifier().setLiteral("newVar");
        identifier.setToken(newToken);

        assertEquals(newToken, identifier.getToken());
        assertEquals("newVar", identifier.getValue());
    }


}
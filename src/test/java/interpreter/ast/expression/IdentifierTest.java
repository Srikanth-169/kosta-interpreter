package interpreter.ast.expression;

import interperter.ast.expression.Identifier;
import interperter.token.Token;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IdentifierTest {

    @Test
    public void testConstructor() {
        Token token = new interperter.token.types.Identifier().setLiteral("variable");
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
        Token token = new interperter.token.types.Identifier().setLiteral("variable");
        Identifier identifier = new Identifier(token);

        assertEquals("variable", identifier.literal());
    }

    @Test
    public void testGetValue() {
        Token token = new interperter.token.types.Identifier().setLiteral("variable");
        Identifier identifier = new Identifier(token);

        assertEquals("variable", identifier.getValue());
    }

    @Test
    public void testSetToken() {
        Token token = new interperter.token.types.Identifier().setLiteral("variable");
        Identifier identifier = new Identifier(token);

        Token newToken = new interperter.token.types.Identifier().setLiteral("newVar");
        identifier.setToken(newToken);

        assertEquals(newToken, identifier.getToken());
        assertEquals("newVar", identifier.getValue());
    }


}
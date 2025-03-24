package interpreter.ast.expression;

import com.github.konstantinevashalomidze.interpreter.ast.expression.FunctionLiteral;
import com.github.konstantinevashalomidze.interpreter.ast.expression.Identifier;
import com.github.konstantinevashalomidze.interpreter.ast.statement.BlockStatement;
import com.github.konstantinevashalomidze.interpreter.token.Token;
import com.github.konstantinevashalomidze.interpreter.token.types.Function;
import com.github.konstantinevashalomidze.interpreter.token.types.Lb;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FunctionLiteralTest {

    @Test
    public void testConstructor() {
        Token token = new Function();
        FunctionLiteral functionLiteral = new FunctionLiteral(token);

        assertEquals(token, functionLiteral.getToken());
    }

    @Test
    public void testLiteral() {
        Token token = new Function();
        FunctionLiteral functionLiteral = new FunctionLiteral(token);

        assertEquals("fn", functionLiteral.literal());
    }

    @Test
    public void testSetToken() {
        Token token = new Function();
        FunctionLiteral functionLiteral = new FunctionLiteral(token);

        Token newToken = new Function();
        functionLiteral.setToken(newToken);

        assertEquals(newToken, functionLiteral.getToken());
    }

    @Test
    public void testSetParameters() {
        Token token = new Function();
        FunctionLiteral functionLiteral = new FunctionLiteral(token);

        List<Identifier> parameters = new ArrayList<>();
        parameters.add(new Identifier(new com.github.konstantinevashalomidze.interpreter.token.types.Identifier().setLiteral("x")));
        parameters.add(new Identifier(new com.github.konstantinevashalomidze.interpreter.token.types.Identifier().setLiteral("y")));

        functionLiteral.setParameters(parameters);

        assertEquals(parameters, functionLiteral.getParameters());
        assertEquals(2, functionLiteral.getParameters().size());
    }

    @Test
    public void testSetBody() {
        Token token = new Function();
        FunctionLiteral functionLiteral = new FunctionLiteral(token);

        BlockStatement body = new BlockStatement(new Lb());
        functionLiteral.setBody(body);

        assertEquals(body, functionLiteral.getBody());
    }

}

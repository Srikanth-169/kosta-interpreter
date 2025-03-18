package interpreter.ast.expression;



import interperter.ast.expression.CallExpression;
import interperter.ast.expression.Expression;
import interperter.ast.expression.Identifier;
import interperter.ast.expression.IntegerLiteral;
import interperter.token.Token;
import interperter.token.types.Integer;
import interperter.token.types.Lb;
import interperter.token.types.Lp;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CallExpressionTest {

    @Test
    public void testConstructor() {
        Token token = new Lp();
        Identifier functionName = new Identifier(token);

        CallExpression callExpression = new CallExpression(token, functionName);

        assertEquals(token, callExpression.getToken());
        assertEquals(functionName, callExpression.getFunction());
        assertTrue(callExpression.getArguments().isEmpty());
    }

    @Test
    public void testLiteral() {
        Token token = new Lp();
        Identifier functionName = new Identifier(new interperter.token.types.Identifier().setLiteral("add"));

        CallExpression callExpression = new CallExpression(token, functionName);

        assertEquals("(", callExpression.literal());
    }

    @Test
    public void testSetToken() {
        Token token = new Lp();
        Identifier functionName = new Identifier(new interperter.token.types.Identifier().setLiteral("add"));

        CallExpression callExpression = new CallExpression(token, functionName);

        Token newToken = new Lp();
        callExpression.setToken(newToken);

        assertEquals(newToken, callExpression.getToken());
    }

    @Test
    public void testSetFunction() {
        Token token = new Lb();
        Identifier functionName = new Identifier(new interperter.token.types.Identifier().setLiteral("add"));

        CallExpression callExpression = new CallExpression(token, functionName);

        Identifier newFunction = new Identifier(new interperter.token.types.Identifier().setLiteral("subtract"));
        callExpression.setFunction(newFunction);

        assertEquals(newFunction, callExpression.getFunction());
    }

    @Test
    public void testSetArguments() {
        Token token = new Lp();
        Identifier functionName = new Identifier(new interperter.token.types.Identifier().setLiteral("add"));

        CallExpression callExpression = new CallExpression(token, functionName);

        List<Expression> args = new ArrayList<>();
        args.add(new IntegerLiteral(new Integer().setLiteral("1")));
        args.add(new IntegerLiteral(new Integer().setLiteral("2")));

        callExpression.setArguments(args);

        assertEquals(args, callExpression.getArguments());
        assertEquals(2, callExpression.getArguments().size());
    }

    @Test
    public void testToString() {
        Token token = new Lp();
        Identifier functionName = new Identifier(new interperter.token.types.Identifier().setLiteral("add"));

        CallExpression callExpression = new CallExpression(token, functionName);

        List<Expression> args = new ArrayList<>();
        args.add(new IntegerLiteral(new Integer().setLiteral("1")));
        args.add(new IntegerLiteral(new Integer().setLiteral("2")));

        callExpression.setArguments(args);

        assertEquals("add(1, 2)", callExpression.toString());
    }

    @Test
    public void testToStringWithNoArguments() {
        Token token = new Lp();
        Identifier functionName = new Identifier(new interperter.token.types.Identifier().setLiteral("add"));

        CallExpression callExpression = new CallExpression(token, functionName);

        assertEquals("add()", callExpression.toString());
    }
}
package interpreter.ast.expression;

import interperter.ast.expression.BooleanLiteral;
import interperter.ast.expression.Expression;
import interperter.ast.expression.IfExpression;
import interperter.ast.statement.BlockStatement;
import interperter.token.Token;
import interperter.token.types.If;
import interperter.token.types.Lb;
import interperter.token.types.True;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IfExpressionTest {

    @Test
    public void testConstructor() {
        Token token = new If();
        IfExpression ifExpression = new IfExpression(token);

        assertEquals(token, ifExpression.getToken());
    }

    @Test
    public void testLiteral() {
        Token token = new If();
        IfExpression ifExpression = new IfExpression(token);

        assertEquals("if", ifExpression.literal());
    }

    @Test
    public void testSetToken() {
        Token token = new If();
        IfExpression ifExpression = new IfExpression(token);

        Token newToken = new If();
        ifExpression.setToken(newToken);

        assertEquals(newToken, ifExpression.getToken());
    }

    @Test
    public void testSetCondition() {
        Token token = new If();
        IfExpression ifExpression = new IfExpression(token);

        Expression condition = new BooleanLiteral(new True());
        ifExpression.setCondition(condition);

        assertEquals(condition, ifExpression.getCondition());
    }

    @Test
    public void testSetConsequence() {
        Token token = new If();
        IfExpression ifExpression = new IfExpression(token);

        BlockStatement consequence = new BlockStatement(new Lb());
        ifExpression.setConsequence(consequence);

        assertEquals(consequence, ifExpression.getConsequence());
    }

    @Test
    public void testSetAlternative() {
        Token token = new If();
        IfExpression ifExpression = new IfExpression(token);

        BlockStatement alternative = new BlockStatement(new Lb());
        ifExpression.setAlternative(alternative);

        assertEquals(alternative, ifExpression.getAlternative());
    }


}
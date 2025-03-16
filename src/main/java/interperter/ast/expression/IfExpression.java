package interperter.ast.expression;


import org.kosta.interperter.ast.statement.BlockStatement;
import org.kosta.interperter.token.Token;

public class IfExpression
    implements Expression
{

    private Token token;
    private Expression condition;
    private BlockStatement consequence;
    private BlockStatement alternative;


    public IfExpression(Token token) {
        this.token = token;
    }

    @Override
    public void expressionNode() {

    }

    @Override
    public String literal() {
        return token.literal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("if").append(" (").append(condition.toString()).append(") ").append(consequence.toString());

        if (alternative != null)
            sb.append(" else ").append(alternative);

        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public BlockStatement getConsequence() {
        return consequence;
    }

    public void setConsequence(BlockStatement consequence) {
        this.consequence = consequence;
    }

    public BlockStatement getAlternative() {
        return alternative;
    }

    public void setAlternative(BlockStatement alternative) {
        this.alternative = alternative;
    }
}

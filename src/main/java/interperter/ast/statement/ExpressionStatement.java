package interperter.ast.statement;


import org.kosta.interperter.ast.expression.Expression;
import org.kosta.interperter.token.Token;

public class ExpressionStatement
    implements Statement
{
    private Token token;
    private Expression expression;


    public ExpressionStatement(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

    public ExpressionStatement(Token token) {
        this.token = token;
    }

    public ExpressionStatement() {

    }




    @Override
    public String literal() {
        return token.literal();
    }

    @Override
    public void statementNode() {

    }

    @Override
    public String toString() {
        if (expression != null) {
            return expression.toString();
        }
        return "";
    }


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}

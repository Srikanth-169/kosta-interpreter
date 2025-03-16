package interperter.ast.statement;


import interperter.ast.expression.Expression;
import interperter.token.Token;

public class ReturnStatement
    implements Statement
{
    Token token;
    Expression value;


    public ReturnStatement(Token token, Expression value) {
        this.token = token;
        this.value = value;
    }
    public ReturnStatement() {

    }

    public ReturnStatement(Token currentToken) {
        this.token = currentToken;
    }


    @Override
    public String literal() {
        return this.token.literal();
    }

    @Override
    public void statementNode() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(token.literal()); // return
        sb.append(" ");
        if (value != null) {
            sb.append(value); // return value
        }

        sb.append(';'); // return value;
        return sb.toString();

    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}

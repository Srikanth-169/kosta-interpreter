package interperter.ast.statement;


import interperter.ast.expression.Expression;
import interperter.ast.expression.Identifier;
import interperter.token.Token;

public class VarStatement
    implements Statement
{
    Token token;
    Identifier name;
    Expression value;


    public VarStatement(Token token, Identifier name, Expression value) {
        this.token = token;
        this.name = name;
        this.value = value;
    }

    public VarStatement() {

    }

    public VarStatement(Token currentToken) {
        token = currentToken;
    }


    @Override
    public String literal() {
        return this.token.literal();
    }

    @Override
    public void statementNode() {

    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append(token.literal())
                .append(' ')
                .append(name.toString())
                .append(" = ");

        if (value != null) {
            sb.append(value);
        }
        sb.append(';');
        return sb.toString();
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public Identifier getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }
}

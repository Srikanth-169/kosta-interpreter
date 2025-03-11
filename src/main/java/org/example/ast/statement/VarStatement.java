package org.example.ast.statement;

import org.example.ast.expression.Expression;
import org.example.ast.expression.Identifier;
import org.example.token.Token;
import org.example.token.TokenType;

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

    public VarStatement(TokenType tokenType, String var) {

    }

    @Override
    public String tokenLiteral() {
        return this.token.getLiteral();
    }

    @Override
    public void statementNode() {

    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append(tokenLiteral())
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

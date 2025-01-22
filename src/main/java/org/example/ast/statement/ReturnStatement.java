package org.example.ast.statement;

import org.example.ast.expression.Expression;
import org.example.token.Token;

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


    @Override
    public String tokenLiteral() {
        return this.token.getLiteral();
    }

    @Override
    public void statementNode() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.tokenLiteral()).append(' ');

        if (this.value != null) {
            sb.append(this.value);
        }

        sb.append(';');
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

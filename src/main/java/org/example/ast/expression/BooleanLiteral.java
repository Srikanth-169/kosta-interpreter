package org.example.ast.expression;

import org.example.token.Token;

public class BooleanLiteral
    implements Expression
{

    private Token token;
    private boolean value;

    public BooleanLiteral(Token token, boolean value) {
        this.token = token;
        this.value = value;
    }

    @Override
    public void expressionNode() {

    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return token.getLiteral();
    }
}

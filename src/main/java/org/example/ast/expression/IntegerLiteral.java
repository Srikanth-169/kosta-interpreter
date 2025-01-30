package org.example.ast.expression;

import org.example.token.Token;

public class IntegerLiteral implements Expression {

    private Token token;
    private int value;

    public IntegerLiteral(Token token) {
        this.token = token;
    }

    public IntegerLiteral(int value) {
        this.value = value;
    }

    @Override
    public void expressionNode() {

    }

    @Override
    public String tokenLiteral() {
        return this.token.getLiteral();
    }


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.token.getLiteral();
    }

}

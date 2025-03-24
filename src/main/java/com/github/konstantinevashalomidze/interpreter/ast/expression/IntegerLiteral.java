package com.github.konstantinevashalomidze.interpreter.ast.expression;


import com.github.konstantinevashalomidze.interpreter.token.Token;

public class IntegerLiteral implements Expression {

    private Token token;

    public IntegerLiteral(Token token) {
        this.token = token;
    }


    @Override
    public void expressionNode() {

    }

    @Override
    public String toString() {
        return "IntegerLiteral (" + token.literal() + ")";
    }

    @Override
    public String literal() {
        return token.literal();
    }


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getValue() {
        return Integer.parseInt(getToken().literal());
    }

}

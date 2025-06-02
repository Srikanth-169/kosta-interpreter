package com.github.konstantinevashalomidze.interpreter.ast.expression;


import com.github.konstantinevashalomidze.interpreter.token.Token;

public class CharacterLiteral implements Expression {

    private Token token;

    public CharacterLiteral(Token token) {
        this.token = token;
    }


    @Override
    public void expressionNode() {

    }

    @Override
    public String toString() {
        return "CharacterLiteral (" + token.literal() + ")";
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

    public char getValue() {
        return getToken().literal().charAt(0);
    }

}

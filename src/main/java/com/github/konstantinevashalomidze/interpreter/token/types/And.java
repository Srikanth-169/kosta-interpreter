package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.AND;


public class And
        implements Token {

    @Override
    public Precedence precedence() {
        return AND;
    }

    @Override
    public String literal() {
        return "&";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

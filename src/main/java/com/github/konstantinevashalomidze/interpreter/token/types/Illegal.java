package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.LOWEST;


public class Illegal
        implements Token {

    @Override
    public Precedence precedence() {
        return LOWEST;
    }


    @Override
    public String literal() {
        return "I";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

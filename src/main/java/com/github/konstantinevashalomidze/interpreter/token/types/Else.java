package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.LOWEST;


public class Else implements Token {


    @Override
    public Precedence precedence() {
        return LOWEST;
    }

    @Override
    public String literal() {
        return "else";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

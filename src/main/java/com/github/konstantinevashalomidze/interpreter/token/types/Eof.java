package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.LOWEST;


public class Eof
        implements Token {
    @Override
    public Precedence precedence() {
        return LOWEST;
    }

    @Override
    public String literal() {
        return "\0";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

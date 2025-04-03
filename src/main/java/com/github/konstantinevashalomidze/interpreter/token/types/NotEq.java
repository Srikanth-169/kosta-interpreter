package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.EQUALS;


public enum NotEq implements Token {
    INSTANCE;

    @Override
    public Precedence precedence() {
        return EQUALS;  // Equality operators
    }

    @Override
    public String literal() {
        return "!=";
    }

}

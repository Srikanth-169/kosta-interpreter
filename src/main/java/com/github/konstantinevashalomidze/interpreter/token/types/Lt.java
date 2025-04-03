package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.COMPARE;


public enum Lt implements Token {
    INSTANCE;
    @Override
    public Precedence precedence() {
        return COMPARE;  // Comparison operators
    }

    @Override
    public String literal() {
        return "<";
    }

}

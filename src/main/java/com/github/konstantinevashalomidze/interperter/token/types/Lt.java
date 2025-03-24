package com.github.konstantinevashalomidze.interperter.token.types;


import com.github.konstantinevashalomidze.interperter.token.Precedence;
import com.github.konstantinevashalomidze.interperter.token.Token;

import static com.github.konstantinevashalomidze.interperter.token.Precedence.COMPARE;


public class Lt implements Token {
    @Override
    public Precedence precedence() {
        return COMPARE;  // Comparison operators
    }

    @Override
    public String literal() {
        return "<";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

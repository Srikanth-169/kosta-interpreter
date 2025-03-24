package com.github.konstantinevashalomidze.interperter.token.types;


import com.github.konstantinevashalomidze.interperter.token.Precedence;
import com.github.konstantinevashalomidze.interperter.token.Token;

import static com.github.konstantinevashalomidze.interperter.token.Precedence.EQUALS;

public class Eq implements Token {


    @Override
    public Precedence precedence() {
        return EQUALS;  // Equality operators have lower precedence than comparisons
    }

    @Override
    public String literal() {
        return "==";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

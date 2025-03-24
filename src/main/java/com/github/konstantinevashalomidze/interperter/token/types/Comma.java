package com.github.konstantinevashalomidze.interperter.token.types;

import com.github.konstantinevashalomidze.interperter.token.Precedence;
import com.github.konstantinevashalomidze.interperter.token.Token;

import static com.github.konstantinevashalomidze.interperter.token.Precedence.LOWEST;

public class Comma implements Token {

    @Override
    public Precedence precedence() {
        return LOWEST;  // Lowest precedence
    }

    @Override
    public String literal() {
        return ",";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

package com.github.konstantinevashalomidze.interperter.token.types;

import com.github.konstantinevashalomidze.interperter.token.Precedence;
import com.github.konstantinevashalomidze.interperter.token.Token;

import static com.github.konstantinevashalomidze.interperter.token.Precedence.PRODUCT;


public class Slash implements Token {

    @Override
    public Precedence precedence() {
        return PRODUCT;  // Same precedence as multiplication
    }

    @Override
    public String literal() {
        return "/";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

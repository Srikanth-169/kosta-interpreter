package com.github.konstantinevashalomidze.interpreter.token.types;

import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.PRODUCT;


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

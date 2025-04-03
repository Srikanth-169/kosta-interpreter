package com.github.konstantinevashalomidze.interpreter.token.types;

import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.PRODUCT;


public enum Slash implements Token {
    INSTANCE;

    @Override
    public Precedence precedence() {
        return PRODUCT;  // Same precedence as multiplication
    }

    @Override
    public String literal() {
        return "/";
    }

}

package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

public enum Asterisk implements Token {
    INSTANCE;

    @Override
    public Precedence precedence() {
        return Precedence.PRODUCT;  // Higher than +/-
    }

    @Override
    public String literal() {
        return "*";
    }

}

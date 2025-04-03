package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

public enum Assign
        implements Token {
    INSTANCE;

    @Override
    public Precedence precedence() {
        return Precedence.LOWEST;
    }

    @Override
    public String literal() {
        return "=";
    }

}




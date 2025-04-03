package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

public enum Bang implements Token {

    INSTANCE;

    @Override
    public Precedence precedence() {
        return Precedence.PREFIX;
    }

    @Override
    public String literal() {
        return "!";
    }

}

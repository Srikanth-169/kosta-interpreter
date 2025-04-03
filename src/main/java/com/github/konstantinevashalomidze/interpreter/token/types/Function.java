package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.LOWEST;


public enum Function implements Token {
    INSTANCE;

    @Override
    public Precedence precedence() {
        return LOWEST;  // Keywords typically don't have operator precedence
    }

    @Override
    public String literal() {
        return "fn";
    }



}

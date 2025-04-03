package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.OR;


public enum Or
        implements Token {
    INSTANCE;
    @Override
    public Precedence precedence() {
        return OR;
    }


    @Override
    public String literal() {
        return "|";
    }

}

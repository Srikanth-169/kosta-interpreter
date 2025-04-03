package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.PREFIX;


public enum MinusPrefix implements Token {
    INSTANCE;

    @Override
    public Precedence precedence() {
        return PREFIX;  // Same precedence as plus
    }

    @Override
    public String literal() {
        return "-";
    }

}

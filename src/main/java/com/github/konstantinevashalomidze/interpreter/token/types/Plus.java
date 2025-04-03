package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.SUM;


public enum Plus implements Token {
    INSTANCE;

    @Override
    public Precedence precedence() {
        return SUM;  // Assuming standard operator precedence
    }

    @Override
    public String literal() {
        return "+";
    }

}

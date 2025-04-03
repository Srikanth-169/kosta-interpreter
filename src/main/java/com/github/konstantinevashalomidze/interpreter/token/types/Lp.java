package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.CALL;


public enum Lp implements Token {
    INSTANCE;


    @Override
    public Precedence precedence() {
        return CALL;  // Grouping operators don't have precedence in the traditional sense
    }

    @Override
    public String literal() {
        return "(";
    }

}

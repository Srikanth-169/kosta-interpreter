package com.github.konstantinevashalomidze.interperter.token.types;


import com.github.konstantinevashalomidze.interperter.token.Precedence;
import com.github.konstantinevashalomidze.interperter.token.Token;

import static com.github.konstantinevashalomidze.interperter.token.Precedence.CALL;


public class Lp implements Token {


    @Override
    public Precedence precedence() {
        return CALL;  // Grouping operators don't have precedence in the traditional sense
    }

    @Override
    public String literal() {
        return "(";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

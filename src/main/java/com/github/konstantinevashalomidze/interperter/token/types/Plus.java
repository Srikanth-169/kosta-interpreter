package com.github.konstantinevashalomidze.interperter.token.types;


import com.github.konstantinevashalomidze.interperter.token.Precedence;
import com.github.konstantinevashalomidze.interperter.token.Token;

import static com.github.konstantinevashalomidze.interperter.token.Precedence.SUM;


public class Plus implements Token {

    @Override
    public Precedence precedence() {
        return SUM;  // Assuming standard operator precedence
    }

    @Override
    public String literal() {
        return "+";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

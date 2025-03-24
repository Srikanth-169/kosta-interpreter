package com.github.konstantinevashalomidze.interperter.token.types;


import com.github.konstantinevashalomidze.interperter.token.Precedence;
import com.github.konstantinevashalomidze.interperter.token.Token;

import static com.github.konstantinevashalomidze.interperter.token.Precedence.PREFIX;


public class MinusPrefix implements Token {

    @Override
    public Precedence precedence() {
        return PREFIX;  // Same precedence as plus
    }

    @Override
    public String literal() {
        return "-";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}

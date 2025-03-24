package com.github.konstantinevashalomidze.interperter.token.types;


import com.github.konstantinevashalomidze.interperter.token.Precedence;
import com.github.konstantinevashalomidze.interperter.token.Token;

import static com.github.konstantinevashalomidze.interperter.token.Precedence.LOWEST;


public class Function implements Token {



    @Override
    public Precedence precedence() {
        return LOWEST;  // Keywords typically don't have operator precedence
    }

    @Override
    public String literal() {
        return "fn";
    }


    public Token setLiteral(String literal) {
        return this;
    }

}

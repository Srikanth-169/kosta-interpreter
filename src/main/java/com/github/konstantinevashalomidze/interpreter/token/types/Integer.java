package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.LOWEST;


public class Integer
    implements Token
{
    private String literal;



    @Override
    public Precedence precedence() {
        return LOWEST;
    }


    @Override
    public String literal() {
        return literal;
    }

    @Override
    public Token setLiteral(String literal) {
        this.literal = literal;
        return this;
    }
}

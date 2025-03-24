package com.github.konstantinevashalomidze.interperter.token.types;


import com.github.konstantinevashalomidze.interperter.token.Precedence;
import com.github.konstantinevashalomidze.interperter.token.Token;

public class Assign
    implements Token
{


    @Override
    public Precedence precedence() {
        return Precedence.LOWEST;
    }

    @Override
    public String literal() {
        return "=";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}




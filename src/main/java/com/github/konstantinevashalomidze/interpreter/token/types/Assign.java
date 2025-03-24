package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

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




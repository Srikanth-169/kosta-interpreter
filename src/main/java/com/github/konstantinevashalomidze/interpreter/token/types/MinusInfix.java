package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.SUM;


public class MinusInfix implements Token {

    @Override
    public Precedence precedence() {
        return SUM;  // Same precedence as plus
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

package com.github.konstantinevashalomidze.interpreter.token.types;


import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import static com.github.konstantinevashalomidze.interpreter.token.Precedence.AND;


public enum And
        implements Token {
    INSTANCE;



    @Override
    public Precedence precedence() {
        return AND;
    }

    @Override
    public String literal() {
        return "&";
    }

}

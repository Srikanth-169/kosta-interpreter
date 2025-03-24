package com.github.konstantinevashalomidze.interperter.token;

/**
 * Represents piece of string as enum
 * @author Konstantine Vashalomidze
 */
public interface Token {


    Precedence precedence();
    String literal();

    Token setLiteral(String string);

}

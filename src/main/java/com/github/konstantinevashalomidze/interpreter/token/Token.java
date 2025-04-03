package com.github.konstantinevashalomidze.interpreter.token;

/**
 * Represents piece of string as enum
 *
 * @author Konstantine Vashalomidze
 */
public interface Token {


    Precedence precedence();

    String literal();

}

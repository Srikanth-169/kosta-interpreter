package com.github.konstantinevashalomidze.interpreter.evaluator.value;

public class Character
        implements Value {
    private final char value;

    public Character(char value) {
        this.value = value;
    }


    @Override
    public String inspect() {
        return "'" + value + "'";
    }

    public char getValue() {
        return value;
    }
}

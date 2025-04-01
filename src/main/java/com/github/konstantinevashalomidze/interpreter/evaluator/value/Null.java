package com.github.konstantinevashalomidze.interpreter.evaluator.value;

public class Null
        implements Value {
    private final String aNull;

    public Null(String aNull) {
        this.aNull = aNull;
    }


    @Override
    public String inspect() {
        return aNull;
    }
}

package com.github.konstantinevashalomidze.interperter.evaluator.value;

public class Null
    implements Value
{
    private String aNull;

    public Null(String aNull) {
        this.aNull = aNull;
    }


    @Override
    public String inspect() {
        return aNull;
    }
}

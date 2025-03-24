package com.github.konstantinevashalomidze.interperter.evaluator.value;

public class Integer
    implements Value
{
    private int value;

    public Integer(int value)
    {
        this.value = value;
    }


    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    public int getValue() {
        return value;
    }
}

package com.github.konstantinevashalomidze.interperter.evaluator.value;

public class Boolean
    implements Value
{
    private boolean value;

    public Boolean(boolean value)
    {
        this.value = value;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    public boolean getValue() {
        return value;
    }
}

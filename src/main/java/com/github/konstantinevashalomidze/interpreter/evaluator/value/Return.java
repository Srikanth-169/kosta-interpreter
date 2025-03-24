package com.github.konstantinevashalomidze.interpreter.evaluator.value;

public class Return
    implements Value
{

    private Value value;

    public Return(Value value)
    {
        this.value = value;
    }



    @Override
    public String inspect() {
        return value.inspect();
    }

    public Value getValue() {
        return value;
    }
}

package org.example.object;

public class Integer
    implements Object
{
    private int value;

    public Integer(int value)
    {
        this.value = value;
    }


    @Override
    public ObjectType objectType() {
        return ObjectType.INTEGER_OBJECT;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }


    public int getValue() {
        return value;
    }
}

package org.example.object;

public class ReturnValue
    implements Object
{

    private Object value;

    public ReturnValue(Object value)
    {
        this.value = value;
    }


    @Override
    public ObjectType objectType() {
        return ObjectType.RETURN_VALUE_OBJECT;
    }

    @Override
    public String inspect() {
        return value.inspect();
    }

    public Object getValue() {
        return value;
    }
}

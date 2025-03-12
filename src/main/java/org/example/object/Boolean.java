package org.example.object;

public class Boolean
    implements Object
{
    private boolean value;

    public Boolean(boolean value)
    {
        this.value = value;
    }

    @Override
    public ObjectType objectType() {
        return ObjectType.BOOLEAN_OBJECT;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }
}

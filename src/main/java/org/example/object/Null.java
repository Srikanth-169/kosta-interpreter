package org.example.object;

public class Null
    implements Object
{
    private String aNull;

    public Null(String aNull) {
        this.aNull = aNull;
    }

    @Override
    public ObjectType objectType() {
        return ObjectType.NULL_OBJECT;
    }

    @Override
    public String inspect() {
        return aNull;
    }
}

package org.example.object;

public class Error
    implements Object
{

    private final String message;

    public Error(String format) {
        this.message = format;
    }

    @Override
    public ObjectType objectType() {
        return ObjectType.ERROR_OBJECT;
    }

    @Override
    public String inspect() {
        return "ERROR: " + message;
    }
}

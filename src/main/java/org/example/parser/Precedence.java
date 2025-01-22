package org.example.parser;

public enum Precedence
{
    LOWEST(0),
    EQUEALS(1), // ==
    LESSG_REATER(2), // < >
    SUM(3), // +
    PRODUCT(4), // *
    PREFIX(5), // -x or !x
    CALL(6); // myFunction(x)

    private int value;

    Precedence(int value)
    {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

package interperter.token;

public enum Precedence
{
    LOWEST(0),
    OR(1),
    AND(2),
    EQUALS(3),
    COMPARE(4),

    SUM(5),

    PRODUCT(6),

    PREFIX(7),

    CALL(8);

    private int number;

    Precedence(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}

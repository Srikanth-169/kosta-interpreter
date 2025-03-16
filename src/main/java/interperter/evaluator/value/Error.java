package interperter.evaluator.value;

public class Error
    implements Value
{

    private final String message;

    public Error(String format) {
        this.message = format;
    }

    @Override
    public String inspect() {
        return "ERROR: " + message;
    }
}

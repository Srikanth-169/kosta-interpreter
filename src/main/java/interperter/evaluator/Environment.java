package interperter.evaluator;

import org.kosta.interperter.evaluator.value.Value;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private Map<String, Value> store;

    private Environment outer; // reference to outer variables

    public Environment() {
        store = new HashMap<>();
    }

    public Environment(Environment environment) {
        store = new HashMap<>();
        this.outer = environment;
    }

    public Value getValue(String name)
    {
        Value value = store.get(name);
        if (value == null)
            value = outer.getValue(name);
        return value;
    }

    public Value putValue(String name, Value value)
    {
        store.put(name, value);
        return value;
    }



}

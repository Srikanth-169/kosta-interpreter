package org.example.object;

import java.util.HashMap;
import java.util.Map;

public class Environment
{
    private Map<String, Object> store;

    private Environment outer;

    public Environment() {
        store = new HashMap<>();
    }

    public Environment(Environment outer) {
        store = new HashMap<>();
        this.outer = outer;
    }


    public Object getValue(String name) {
        Object value = store.get(name);
        if (value == null) {
            value = outer.getValue(name);
        }
        return value;
    }

    public Object setPair(String name, Object value) {
        store.put(name, value);
        return value;
    }


}

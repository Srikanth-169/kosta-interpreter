package com.github.konstantinevashalomidze.interperter.parser.prefix;


import com.github.konstantinevashalomidze.interperter.ast.expression.Expression;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface Prefix {
    Expression parseFn() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

}

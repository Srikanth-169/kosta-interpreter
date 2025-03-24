package com.github.konstantinevashalomidze.interpreter.parser.prefix;


import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface Prefix {
    Expression parseFn() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

}

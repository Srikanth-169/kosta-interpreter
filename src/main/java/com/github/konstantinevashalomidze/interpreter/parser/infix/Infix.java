package com.github.konstantinevashalomidze.interpreter.parser.infix;


import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface Infix {
    Expression parseFn(Expression expression) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}

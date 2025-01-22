package org.example.parser;

import org.example.ast.expression.Expression;

@FunctionalInterface
public interface Infix {
    Expression parseFn(Expression expression);
}

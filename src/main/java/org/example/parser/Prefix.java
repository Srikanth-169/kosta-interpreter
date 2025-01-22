package org.example.parser;

import org.example.ast.expression.Expression;

@FunctionalInterface
public interface Prefix {
    Expression parseFn();
}

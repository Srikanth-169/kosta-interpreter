package interperter.parser.infix;


import interperter.ast.expression.Expression;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface Infix {
    Expression parseFn(Expression expression) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}

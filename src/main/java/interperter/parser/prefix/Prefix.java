package interperter.parser.prefix;

import org.kosta.interperter.ast.expression.Expression;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface Prefix {
    Expression parseFn() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

}

package org.example.evaluator;

import org.example.ast.expression.*;
import org.example.ast.node.Node;
import org.example.ast.node.Program;
import org.example.ast.statement.*;
import org.example.object.*;
import org.example.object.Boolean;
import org.example.object.Error;
import org.example.object.Integer;
import org.example.object.Object;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Konstantine Vashalomidze
 */
public class Evaluator
{
    private Boolean TRUE = new Boolean(true);
    private Boolean FALSE = new Boolean(false);

    private Null NULL = new Null("null");

    private Environment environment;
    private Environment outer;

    public Evaluator(Environment environment) {
        this.environment = environment;
    }

    public Object evaluate(Node node) {

        if (node instanceof Program)
        {
            return evaluateProgram(((Program) node).getStatements());
        }
        else if (node instanceof ExpressionStatement)
        {
            return evaluate(((ExpressionStatement) node).getExpression());
        }
        else if (node instanceof PrefixExpression)
        {
            Object right  = evaluate(((PrefixExpression) node).getRight());
            if (isError(right))
            {
                return right;
            }
            return evaluatePrefixExpression(((PrefixExpression) node).getOperator(), right);
        }
        else if (node instanceof InfixExpression)
        {
            Object left = evaluate(((InfixExpression) node).getLeft());
            if (isError(left))
                return left;

            Object right = evaluate(((InfixExpression) node).getRight());
            if (isError(right))
                return right;


            return evaluateInfixExpression(((InfixExpression) node).getOperator(), right, left);

        }
        else if (node instanceof BlockStatement)
        {
            return evaluateBlockStatement(((BlockStatement) node).getStatements());
        }
        else if (node instanceof IfExpression)
        {
            return evaluateIfExpression(node);
        }
        else if (node instanceof ReturnStatement)
        {
            Object value = evaluate(((ReturnStatement) node).getValue());
            if (isError(value))
                return value;
            return new ReturnValue(value);
        }
        else if (node instanceof VarStatement varStatement) {
            Object value = evaluate(varStatement.getValue());
            if (isError(value))
                return value;
            return environment.setPair(varStatement.getName().getValue(), value);
        }
        else if (node instanceof Identifier) {
            return evaluateIdentifier(node);
        }
        else if (node instanceof CallExpression) {
            Object function = evaluate(((CallExpression) node).getFunction());
            if (isError(function))
                return function;
            List<Object> args = evaluateExpressions(((CallExpression) node).getArguments());
            if (args.size() == 1  && isError(args.getFirst()))
            {
                return args.getFirst();
            }
            return applyFunction(function, args);
        }
        else if (node instanceof FunctionLiteral) {
            List<Identifier> params = ((FunctionLiteral) node).getParameters();
            BlockStatement body = ((FunctionLiteral) node).getBody();
            return new Function(params, body, environment);
        }
        else if (node instanceof IntegerLiteral)
        {
            return new Integer(((IntegerLiteral) node).getValue());
        }
        else if (node instanceof BooleanLiteral)
        {
            return ((BooleanLiteral) node).getValue() ? TRUE : FALSE;
        }

        return NULL;
    }

    private Object applyFunction(Object function, List<Object> args) {
        Function fn;
        try {
            fn = (Function) function;
        } catch (ClassCastException ex) {
            return new Error(String.format("Not a function: %s", function.objectType()));
        }

        Environment extendedEnv = extendFunctionEnv(function, args);
        outer = environment;
        environment = extendedEnv;
        Object evaluated = evaluate(fn.getBody());
        environment = outer;
        outer = null;
        return unwrapReturnValue(evaluated);

    }

    private Object unwrapReturnValue(Object evaluated) {
        try {
            return ((ReturnValue) evaluated).getValue();

        } catch (ClassCastException ex) {
            new Error("Cannot unwrap return value");
        }

        return evaluated;
    }

    private Environment extendFunctionEnv(Object function, List<Object> args) {
        Environment enclosedEnv = new Environment(((Function) function).getEnvironment());
        List<Identifier> params = ((Function) function).getParameters();
        for (int i = 0; i < params.size(); i++) {
            enclosedEnv.setPair(params.get(i).getValue(), args.get(i));
        }
        return enclosedEnv;
    }

    private List<Object> evaluateExpressions(List<Expression> arguments) {
        List<Object> result = new ArrayList<>();

        for (Expression expression : arguments)
        {
            Object evaluated = evaluate(expression);
            if (isError(evaluated))
                return List.of(evaluated);
            result.add(evaluated);
        }

        return result;
    }

    private Object evaluateIdentifier(Node node) {
        Identifier identifier = (Identifier) node;
        Object value = environment.getValue(identifier.getValue());
        if (value == null) {
            return newError("Identifier not found " + identifier.getValue());
        }
        return value;
    }

    private Object evaluateBlockStatement(List<Statement> statements) {
        Object result = null;
        for (Statement statement : statements)
        {
            result = evaluate(statement);

            if (result != null) {
                ObjectType rt = result.objectType();
                if (rt == ObjectType.RETURN_VALUE_OBJECT || rt == ObjectType.ERROR_OBJECT)
                {
                    return result;
                }
            }
        }
        return result;
    }

    private Object evaluateProgram(List<Statement> statements) {
        Object result = null;

        for (Statement statement : statements)
        {
            result = evaluate(statement);

            try {
                Object returnValue = ((ReturnValue) result).getValue();
                if (returnValue != null)
                    return returnValue;
            } catch (ClassCastException ignored) {
                return result;
            }

        }

        return result;
    }

    private Object evaluateIfExpression(Node node) {
        IfExpression ifExpression = ((IfExpression) node);
        Object condition = evaluate(ifExpression.getCondition());

        if (isError(condition))
        {
            return condition;
        }

        if (isNotFalseAndNull(condition))
        {
            return evaluate(ifExpression.getConsequence());
        }
        else if (ifExpression.getAlternative() != null)
        {
            return evaluate(ifExpression.getAlternative());
        }
        else
        {
            return null;
        }
    }

    private boolean isNotFalseAndNull(Object condition) {
        if (condition.equals(NULL))
            return false;
        else if (condition.equals(TRUE))
            return true;
        else return !condition.equals(FALSE);
    }

    private Object evaluateInfixExpression(String operator, Object right, Object left) {
        if (left.objectType() != right.objectType()) {
            return newError("Type mismatch: %s %s %s", left.objectType(), operator, right.objectType());
        }
        else if (left.objectType() == ObjectType.INTEGER_OBJECT && right.objectType() == ObjectType.INTEGER_OBJECT)
            return evaluateIntegerInfixExpression(operator, left, right);
        else if (operator.equals("=="))
            return right == left ? TRUE : FALSE;
        else if (operator.equals("!="))
            return right != left ? TRUE : FALSE;
        else
            return newError("unknown operator: %s %s %s",
                    left.objectType(), operator, right.objectType());
    }

    private Object evaluateIntegerInfixExpression(String operator, Object left, Object right) {
        return switch (operator) {
            case "+" -> new Integer(((Integer) left).getValue() + ((Integer) right).getValue());
            case "-" -> new Integer(((Integer) left).getValue() - ((Integer) right).getValue());
            case "*" -> new Integer(((Integer) left).getValue() * ((Integer) right).getValue());
            case "/" -> new Integer(((Integer) left).getValue() / ((Integer) right).getValue());
            case "<" -> ((Integer) left).getValue() < ((Integer) right).getValue() ? TRUE : FALSE;
            case ">" -> ((Integer) left).getValue() > ((Integer) right).getValue() ? TRUE : FALSE;
            case "==" -> ((Integer) left).getValue() == ((Integer) right).getValue() ? TRUE : FALSE;
            case "!=" -> ((Integer) left).getValue() != ((Integer) right).getValue() ? TRUE : FALSE;
            default -> new Error(String.format("Unknown operator: %s %s %s", left.objectType(), operator, right.objectType()));
        };
    }

    private Object evaluatePrefixExpression(String operator, Object right) {
        return switch (operator) {
            case "!" ->
                evaluateBangOperatorExpression(right);
            case "-" -> evaluateMinusOperatorExpression(right);
            default -> newError("Unknown operator: %s%s", operator, right.objectType());
        };
    }

    private Object evaluateMinusOperatorExpression(Object right) {
        if (right.objectType() != ObjectType.INTEGER_OBJECT)
        {
            return newError("Unknown operator: -%s", right.objectType());
        }

        int value = ((Integer) right).getValue();
        return new Integer(-value);
    }

    private Object evaluateBangOperatorExpression(Object right) {
        return switch (right.inspect())
        {
            case "false", "null" -> TRUE;
            default -> FALSE;
        };
    }



    private Error newError(String message, java.lang.Object... a) {
        return new Error(String.format(message, a));
    }

    private boolean isError(Object object)
    {
        if (object != null)
        {
            return object.objectType() == ObjectType.ERROR_OBJECT;
        }
        return false;
    }

}

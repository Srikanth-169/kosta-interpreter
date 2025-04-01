package com.github.konstantinevashalomidze.interpreter.evaluator;


import com.github.konstantinevashalomidze.interpreter.ast.expression.*;
import com.github.konstantinevashalomidze.interpreter.ast.node.Node;
import com.github.konstantinevashalomidze.interpreter.ast.node.Program;
import com.github.konstantinevashalomidze.interpreter.ast.statement.*;
import com.github.konstantinevashalomidze.interpreter.evaluator.value.*;
import com.github.konstantinevashalomidze.interpreter.evaluator.value.Boolean;
import com.github.konstantinevashalomidze.interpreter.evaluator.value.Error;
import com.github.konstantinevashalomidze.interpreter.evaluator.value.Integer;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {

    private final Boolean TRUE = new Boolean(true);
    private final Boolean FALSE = new Boolean(false);

    private final Null aNull = new Null("null");

    private Environment environment;

    private Environment outer;

    public Evaluator(Environment environment) {
        this.environment = environment;
    }

    public Value evaluate(Node node) {
        if (node instanceof Program program) {
            return evaluateProgram(program.getStatements());
        } else if (node instanceof ExpressionStatement expressionStatement) {
            return evaluate(expressionStatement.getExpression());
        } else if (node instanceof PrefixExpression prefixExpression) {
            Value right = evaluate(prefixExpression.getRight());
            if (isError(right))
                return right;
            return evaluatePrefixExpression(prefixExpression.getOperator(), right);
        } else if (node instanceof InfixExpression infixExpression) {
            Value left = evaluate(infixExpression.getLeft());
            if (isError(left))
                return left;

            Value right = evaluate(infixExpression.getRight());
            if (isError(right))
                return right;

            return evaluateInfixExpression(infixExpression.getOperator(), left, right);
        } else if (node instanceof BlockStatement blockStatement) {
            return evaluateBlockStatement(blockStatement.getStatements());
        } else if (node instanceof IfExpression ifExpression) {
            return evaluateIfExpression(ifExpression);
        } else if (node instanceof ReturnStatement returnStatement) {
            Value value = evaluate(returnStatement.getValue());
            if (isError(value))
                return value;
            return new Return(value);
        } else if (node instanceof VarStatement varStatement) {
            Value value = evaluate(varStatement.getValue());
            if (isError(value))
                return value;
            return environment.putValue(varStatement.getName().getValue(), value);
        } else if (node instanceof Identifier identifier) {
            return evaluateIdentifier(identifier);
        } else if (node instanceof CallExpression callExpression) {
            Value function = evaluate(callExpression.getFunction());
            if (isError(function))
                return function;
            List<Value> args = evaluateExpressions(callExpression.getArguments());
            if (args.size() == 1 && isError(args.getFirst())) {
                return args.getFirst();
            }
            return applyFunction(function, args);
        } else if (node instanceof FunctionLiteral functionLiteral) {
            List<Identifier> params = functionLiteral.getParameters();
            BlockStatement body = functionLiteral.getBody();
            return new Function(params, body, environment);
        } else if (node instanceof IntegerLiteral integerLiteral) {
            return new Integer(integerLiteral.getValue());
        } else if (node instanceof BooleanLiteral booleanLiteral) {
            return booleanLiteral.getValue() ? TRUE : FALSE;
        }

        return aNull;
    }


    private Value evaluateIfExpression(IfExpression ifExpression) {
        Value condition = evaluate(ifExpression.getCondition());

        if (isError(condition)) {
            return condition;
        }

        if (isNotFalseAndNull(condition)) {
            return evaluate(ifExpression.getConsequence());
        } else if (ifExpression.getAlternative() != null) {
            return evaluate(ifExpression.getAlternative());
        } else {
            return aNull;
        }
    }

    private boolean isNotFalseAndNull(Value condition) {
        if (condition.equals(aNull))
            return false;
        else if (condition.equals(TRUE))
            return true;
        else return !condition.equals(FALSE);
    }

    /**
     * Evaluates function with provided arguments and returns value
     *
     * @param function function to evaluate
     * @param args     passed arguments
     * @return evaluated value
     */
    private Value applyFunction(Value function, List<Value> args) {
        Function fn;
        try {
            fn = (Function) function;
        } catch (ClassCastException ex) {
            return newError("Not a function: %s", function.getClass().getSimpleName());
        }

        Environment extendedEnv = extendFunctionEnv(function, args);
        outer = environment;
        environment = extendedEnv;
        Value evaluated = evaluate(fn.getBody());
        environment = outer;
        outer = null;
        return unwrapReturnValue(evaluated);
    }


    private Value unwrapReturnValue(Value evaluated) {
        if (evaluated instanceof Return returnValue) {
            return returnValue.getValue();
        }
        return evaluated;
    }

    private Environment extendFunctionEnv(Value function, List<Value> args) {
        Environment enclosedEnv = new Environment(((Function) function).getEnvironment());
        List<Identifier> params = ((Function) function).getParameters();
        for (int i = 0; i < params.size(); i++) {
            enclosedEnv.putValue(params.get(i).getValue(), args.get(i));
        }
        return enclosedEnv;
    }

    private List<Value> evaluateExpressions(List<Expression> arguments) {
        List<Value> result = new ArrayList<>();

        for (Expression expression : arguments) {
            Value evaluated = evaluate(expression);
            if (isError(evaluated))
                return List.of(evaluated);
            result.add(evaluated);
        }

        return result;
    }


    private Value evaluateIdentifier(Identifier identifier) {
        Value value = environment.getValue(identifier.getValue());
        if (value == null) {
            return newError("Identifier not found " + identifier.getValue());
        }
        return value;
    }


    /**
     * Evaluates block statements and gives back value of the last evaluated statement
     *
     * @param statements statements that are in block statement
     * @return value of last evaluated statement
     */
    private Value evaluateBlockStatement(List<Statement> statements) {
        Value evaluated = null;
        for (Statement statement : statements) {
            evaluated = evaluate(statement);

            if (evaluated != null) {
                if (evaluated instanceof Return || isError(evaluated))
                    return evaluated;
            }
        }

        return evaluated;
    }

    /**
     * Evaluates infix expressions like 'true | false'
     *
     * @param operator operator like: +, -, & ...
     * @param left     left value operand
     * @param right    right value operand
     * @return applies infix operation to operands left and right and evaluates
     */
    private Value evaluateInfixExpression(String operator, Value left, Value right) {
        if (!left.getClass().getSimpleName().equals(right.getClass().getSimpleName()))
            return newError("Type mismatch: %s %s %s", left.getClass().getSimpleName(), operator, right.getClass().getSimpleName());
        else if (left instanceof Integer && right instanceof Integer)
            return evaluateIntegerInfixExpression(operator, left, right);
        else if (left instanceof Boolean && right instanceof Boolean)
            return evaluateBooleanInfixExpression(operator, left, right);
        else if (operator.equals("=="))
            return left == right ? TRUE : FALSE;
        else if (operator.equals("!="))
            return right != left ? TRUE : FALSE;
        else
            return newError("Unknown operation: %s %s %s",
                    left.getClass().getSimpleName(), operator, right.getClass().getSimpleName());


    }

    private Value evaluateBooleanInfixExpression(String operator, Value left, Value right) {
        return switch (operator) {
            case "|" -> new Boolean(((Boolean) left).getValue() || ((Boolean) right).getValue());
            case "&" -> new Boolean(((Boolean) left).getValue() && ((Boolean) right).getValue());
            default ->
                    new Error(String.format("Unknown operation: %s %s %s", left.getClass().getSimpleName(), operator, right.getClass().getSimpleName()));
        };
    }

    private Value evaluateIntegerInfixExpression(String operator, Value left, Value right) {
        return switch (operator) {
            case "+" -> new Integer(((Integer) left).getValue() + ((Integer) right).getValue());
            case "-" -> new Integer(((Integer) left).getValue() - ((Integer) right).getValue());
            case "*" -> new Integer(((Integer) left).getValue() * ((Integer) right).getValue());
            case "/" -> new Integer(((Integer) left).getValue() / ((Integer) right).getValue());
            case "<" -> ((Integer) left).getValue() < ((Integer) right).getValue() ? TRUE : FALSE;
            case ">" -> ((Integer) left).getValue() > ((Integer) right).getValue() ? TRUE : FALSE;
            case "==" -> ((Integer) left).getValue() == ((Integer) right).getValue() ? TRUE : FALSE;
            case "!=" -> ((Integer) left).getValue() != ((Integer) right).getValue() ? TRUE : FALSE;
            default ->
                    new Error(String.format("Unknown operation: %s %s %s", left.getClass().getSimpleName(), operator, right.getClass().getSimpleName()));
        };
    }


    /**
     * Determines if the value is Error
     *
     * @param value value of type Value
     * @return true if value is instance of Error value false otherwise.
     */
    private boolean isError(Value value) {
        return value instanceof Error; // if value == null evaluates to false
    }

    /**
     * Evaluates and returns value of the prefix expression.
     *
     * @param operator "+" or "-"
     * @param right    operand to apply operator
     * @return evaluated result for example if operand was 5 and operator - this evaluates to -5
     */
    private Value evaluatePrefixExpression(String operator, Value right) {
        return switch (operator) {
            case "!" -> evaluateBangOperatorExpression(right);
            case "-" -> evaluateMinusPrefixExpression(right);
            default -> newError("Unknown operator: %s%s", operator, right.getClass().getSimpleName());
        };
    }

    private Value evaluateMinusPrefixExpression(Value right) {
        if (!(right instanceof Integer))
            return newError("Unknown operator: -%s", right.getClass().getSimpleName());

        int value = ((Integer) right).getValue();
        return new Integer(-value);
    }

    private Value evaluateBangOperatorExpression(Value right) {
        return switch (right.inspect()) {
            case "false", "null" -> TRUE;
            default -> FALSE;
        };
    }


    private Value evaluateProgram(List<Statement> statements) {
        Value evaluated = null;
        for (Statement statement : statements) {
            evaluated = evaluate(statement);

            if (evaluated instanceof Return returnValue) // Terminate execution of statements upon after seeing return
                return returnValue;
        }
        return evaluated;
    }


    /**
     * Wraps around String.format(format, args) and returns Error value.
     */
    private Error newError(String message, Object... a) {
        return new Error(String.format(message, a));
    }

}

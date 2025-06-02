package com.github.konstantinevashalomidze.interpreter.evaluator;


import com.github.konstantinevashalomidze.interpreter.ast.expression.*;
import com.github.konstantinevashalomidze.interpreter.ast.node.Node;
import com.github.konstantinevashalomidze.interpreter.ast.node.Program;
import com.github.konstantinevashalomidze.interpreter.ast.statement.*;
import com.github.konstantinevashalomidze.interpreter.evaluator.value.*;
import com.github.konstantinevashalomidze.interpreter.evaluator.value.Boolean;
import com.github.konstantinevashalomidze.interpreter.evaluator.value.Character;
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
            } else if (node instanceof CharacterLiteral characterLiteral) {
                return new Character(characterLiteral.getValue());
            }
        else if (node instanceof BooleanLiteral booleanLiteral) {
            return booleanLiteral.getValue() ? TRUE : FALSE;
        }

        return newError("EVALUATION_ERROR: Unsupported node type: %s",
                node != null ? node.getClass().getSimpleName() : "null");
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
            return newError("TYPE_ERROR: Cannot call non-function value. Expected Function but got: %s",
                    getTypeName(function));
        }

        // Check if number of arguments matches number of parameters
        if (fn.getParameters().size() != args.size()) {
            return newError("ARGUMENT_ERROR: Wrong number of arguments: expected %d, got %d",
                    fn.getParameters().size(), args.size());
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
        Value value;
        if (identifier.getValue().equals("null")) {
            value = aNull;
        } else {
            value = environment.getValue(identifier.getValue());
        }

        if (value == null) {
            return newError("REFERENCE_ERROR: Undefined variable '%s'", identifier.getValue());
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
        // Check for null values first
        if (left == null || right == null) {
            return newError("EVALUATION_ERROR: Cannot perform operation on null value");
        }

        // Use direct class comparison instead of string comparison of class names
        if (left.getClass() != right.getClass())
            return newError("TYPE_ERROR: Cannot apply operator '%s' to different types: %s and %s",
                    operator, getTypeName(left), getTypeName(right));
        else if (left instanceof Integer && right instanceof Integer)
            return evaluateIntegerInfixExpression(operator, left, right);
        else if (left instanceof Boolean && right instanceof Boolean)
            return evaluateBooleanInfixExpression(operator, left, right);
        else if (operator.equals("=="))
            return left == right ? TRUE : FALSE;
        else if (operator.equals("!="))
            return right != left ? TRUE : FALSE;
        else
            return newError("OPERATOR_ERROR: Unsupported operation: %s %s %s",
                    getTypeName(left), operator, getTypeName(right));
    }

    private Value evaluateBooleanInfixExpression(String operator, Value left, Value right) {
        return switch (operator) {
            case "|" -> new Boolean(((Boolean) left).getValue() || ((Boolean) right).getValue());
            case "&" -> new Boolean(((Boolean) left).getValue() && ((Boolean) right).getValue());
            case "==" -> ((Boolean) left).getValue() == ((Boolean) right).getValue() ? TRUE : FALSE;
            case "!=" -> ((Boolean) left).getValue() != ((Boolean) right).getValue() ? TRUE : FALSE;
            default ->
                    newError("OPERATOR_ERROR: Unsupported boolean operator: '%s'", operator);
        };
    }

    private Value evaluateIntegerInfixExpression(String operator, Value left, Value right) {
        int leftVal = ((Integer) left).getValue();
        int rightVal = ((Integer) right).getValue();

        return switch (operator) {
            case "+" -> new Integer(leftVal + rightVal);
            case "-" -> new Integer(leftVal - rightVal);
            case "*" -> new Integer(leftVal * rightVal);
            case "/" -> {
                if (rightVal == 0) {
                    yield newError("ARITHMETIC_ERROR: Division by zero");
                }
                yield new Integer(leftVal / rightVal);
            }
            case "<" -> leftVal < rightVal ? TRUE : FALSE;
            case ">" -> leftVal > rightVal ? TRUE : FALSE;
            case "==" -> leftVal == rightVal ? TRUE : FALSE;
            case "!=" -> leftVal != rightVal ? TRUE : FALSE;
            case "<=" -> leftVal <= rightVal ? TRUE : FALSE;
            case ">=" -> leftVal >= rightVal ? TRUE : FALSE;
            default ->
                    newError("OPERATOR_ERROR: Unsupported integer operator: '%s'", operator);
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
            case "+" -> evaluatePlusPrefixExpression(right);
            default -> newError("SYNTAX_ERROR: Unknown prefix operator: '%s'", operator);
        };
    }

    private Value evaluateMinusPrefixExpression(Value right) {
        if (!(right instanceof Integer))
            return newError("TYPE_ERROR: Cannot negate non-integer value. Got: %s",
                    getTypeName(right));

        int value = ((Integer) right).getValue();
        return new Integer(-value);
    }

    private Value evaluatePlusPrefixExpression(Value right) {
        if (!(right instanceof Integer))
            return newError("TYPE_ERROR: Cannot apply unary plus to non-integer value. Got: %s",
                    getTypeName(right));

        // Unary plus doesn't change the value, but it's here for completeness
        return right;
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
                return returnValue.getValue(); // Unwrap return value at program level

            if (isError(evaluated)) // Terminate execution when encountering an error
                return evaluated;
        }
        return evaluated != null ? evaluated : aNull; // Return null if no statements or if last statement returned null
    }

    /**
     * Creates a new Error value with a formatted message and error type prefix.
     *
     * @param message The error message format string
     * @param args Arguments for the format string
     * @return An Error value with the formatted message
     */
    private Error newError(String message, Object... args) {
        return new Error(String.format(message, args));
    }

    /**
     * Returns a user-friendly type name for a Value object.
     * This is more consistent and readable than using getClass().getSimpleName().
     *
     * @param value The Value object to get type name for
     * @return A string representing the type name
     */
    private String getTypeName(Value value) {
        if (value instanceof Integer) return "Integer";
        if (value instanceof Character) return "Character";
        if (value instanceof Boolean) return "Boolean";
        if (value instanceof Function) return "Function";
        if (value instanceof Null) return "Null";
        if (value instanceof Return) return "Return";
        if (value instanceof Error) return "Error";
        return value.getClass().getSimpleName(); // Fallback
    }
}
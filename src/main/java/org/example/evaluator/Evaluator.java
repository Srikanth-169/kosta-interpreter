package org.example.evaluator;

import org.example.ast.expression.*;
import org.example.ast.node.Node;
import org.example.ast.node.Program;
import org.example.ast.statement.BlockStatement;
import org.example.ast.statement.ExpressionStatement;
import org.example.ast.statement.Statement;
import org.example.object.*;
import org.example.object.Boolean;
import org.example.object.Integer;
import org.example.object.Object;

import java.util.List;

public class Evaluator
{
    private Boolean TRUE = new Boolean(true);
    private Boolean FALSE = new Boolean(false);

    private Null NULL = new Null("null");


    public Object evaluate(Node node) {
        if (node instanceof Program)
        {
            return evaluateStatements(((Program) node).getStatements());
        }
        else if (node instanceof ExpressionStatement)
        {
            return evaluate(((ExpressionStatement) node).getExpression());
        }
        else if (node instanceof PrefixExpression)
        {
            Object right  = evaluate(((PrefixExpression) node).getRight());
            return evaluatePrefixExpression(((PrefixExpression) node).getOperator(), right);
        }
        else if (node instanceof InfixExpression)
        {
            Object right = evaluate(((InfixExpression) node).getRight());
            Object left = evaluate(((InfixExpression) node).getLeft());
            return evaluateInfixExpression(((InfixExpression) node).getOperator(), right, left);

        }
        else if (node instanceof BlockStatement)
        {
            return evaluateStatements(((BlockStatement) node).getStatements());
        }
        else if (node instanceof IfExpression)
        {
            return evaluateIfExpression(node);
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

    private Object evaluateIfExpression(Node node) {
        IfExpression ifExpression = ((IfExpression) node);
        Object condition = evaluate(ifExpression.getCondition());

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
        if (left.objectType() == ObjectType.INTEGER_OBJECT && right.objectType() == ObjectType.INTEGER_OBJECT)
            return evaluateIntegerInfixExpression(operator, left, right);
        else if (operator.equals("=="))
            return right == left ? TRUE : FALSE;
        else if (operator.equals("!="))
            return right != left ? TRUE : FALSE;
        else
            return NULL;
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
            default -> NULL;
        };
    }

    private Object evaluatePrefixExpression(String operator, Object right) {
        return switch (operator) {
            case "!" ->
                evaluateBangOperatorExpression(right);
            case "-" -> evaluateMinusOperatorExpression(right);
            default -> NULL;
        };
    }

    private Object evaluateMinusOperatorExpression(Object right) {
        if (right.objectType() != ObjectType.INTEGER_OBJECT)
        {
            return NULL;
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


    public Object evaluateStatements(List<Statement> statements)
    {
        Object result = null;

        for (Statement statement : statements)
            result = evaluate(statement);

        return result;
    }




}

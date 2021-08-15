package com.cbtl.expression.operation;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.expression.ExpressionNode;
import com.cbtl.error.NotSupportedOperationException;
import com.cbtl.error.WrongNumberOfArgumentsException;
import com.cbtl.expression.helper.TriFunction;
import com.cbtl.expression.helper.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OperationType {
    private static final Logger logger = LogManager.getLogger(OperationType.class);

    public static TriFunction<Client, Product, List<ExpressionNode>, Try> getForString(String operation) {
        logger.debug("Looking for operation {}", operation);
        return switch (operation) {
            case "AND" -> OperationType.AND;
            case "OR" -> OperationType.OR;
            case "NOT" -> OperationType.NOT;
            default -> {
                logger.error("Not supported operation {}", operation);
                throw new NotSupportedOperationException("Not supported operation: " + operation);
            }
        };
    }

    public static void requireArgumentsOrFail(List<ExpressionNode> expressions, int number) {
        if (expressions.size() != number) {
            logger.error("Wrong number of arguments for operation, required {} ", number);
            throw new WrongNumberOfArgumentsException("Wrong number of arguments, required: " + number);
        }
    }

    public static TriFunction<Client, Product, List<ExpressionNode>, Try> AND = ((client, product, expressions) -> {
        OperationType.requireArgumentsOrFail(expressions, 2);

        Try firstExpressionTry = expressions.get(0).checkRestrictions(client, product);
        Try secondExpressionTry = expressions.get(1).checkRestrictions(client, product);

        boolean test = firstExpressionTry.getResult() && secondExpressionTry.getResult();

        return Try.builder()
                .result(test)
                .reason("Both must be valid")
                .childTries(List.of(firstExpressionTry, secondExpressionTry))
                .build();
    });

    public static TriFunction<Client, Product, List<ExpressionNode>, Try> OR = ((client, product, expressions) -> {
        OperationType.requireArgumentsOrFail(expressions, 2);

        Try firstExpressionTry = expressions.get(0).checkRestrictions(client, product);
        Try secondExpressionTry = expressions.get(1).checkRestrictions(client, product);

        boolean test = firstExpressionTry.getResult() || secondExpressionTry.getResult();

        return Try.builder()
                .result(test)
                .reason("At least one must be valid")
                .childTries(List.of(firstExpressionTry, secondExpressionTry))
                .build();
    });

    public static TriFunction<Client, Product, List<ExpressionNode>, Try> NOT = ((client, product, expressions) -> {
        OperationType.requireArgumentsOrFail(expressions, 1);

        Try expressionTry = expressions.get(0).checkRestrictions(client, product);

        boolean test = !expressionTry.getResult();

        return Try.builder()
                .result(test)
                .reason("Cannot be")
                .childTries(List.of(expressionTry))
                .build();
    });
}

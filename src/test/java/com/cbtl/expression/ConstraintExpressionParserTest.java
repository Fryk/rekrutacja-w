package com.cbtl.expression;

import com.cbtl.error.MalformedInputException;
import com.cbtl.error.NotSupportedOperationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstraintExpressionParserTest {
    @Test
    void getExpression() {
        // Given
        String operator = "OR";
        String test = String.format("%s('ONE','TWO')", operator);

        // When
        ExpressionNode result = ConstraintExpressionParser.getExpression(test);

        // Then
        assertTrue(result instanceof OperationNode);
        OperationNode castedResult = (OperationNode) result;
        assertEquals(operator, castedResult.getOperator());
        assertEquals(2, castedResult.getExpressions().size());
        assertTrue(castedResult.getExpressions().stream().allMatch(expressionNode -> expressionNode.getClass().equals(ConstraintNode.class)));
    }

    @Test
    void getExpression_throwsMalformedInputException() {
        // Given
        String test = "TEST('UNO',DOS'";

        // When
        Exception exception = assertThrows(MalformedInputException.class, () -> ConstraintExpressionParser.getExpression(test));

        // Then
        assertTrue(exception.getMessage().contains("Malformed"));
    }

//    @Test
//    void getExpression_throwsNotSupportedOperationException() {
//        // Given
//        String test = "LITWO(OJCZYZNO('MOJA'))";
//
//        // When
//        Exception exception = assertThrows(NotSupportedOperationException.class, () -> ConstraintExpressionParser.getExpression(test));
//
//        // Then
//        assertTrue(exception.getMessage().contains("Not supported operation: LITWO"));
//    }
}

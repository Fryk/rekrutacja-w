package com.cbtl.expression.operation;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.error.MalformedInputException;
import com.cbtl.error.NotSupportedOperationException;
import com.cbtl.error.WrongNumberOfArgumentsException;
import com.cbtl.expression.ConstraintExpressionParser;
import com.cbtl.expression.ExpressionNode;
import com.cbtl.expression.helper.TriFunction;
import com.cbtl.expression.helper.Try;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperationTypeTest {
    @Test
    void getForString() {
        // Given
        String operation = "AND";

        // When
        TriFunction<Client, Product, List<ExpressionNode>, Try> result = OperationType.getForString(operation);

        // Then
        assertNotNull(result);
    }

    @Test
    void getForString_throwsNotSupportedOperationException() {
        // Given
        String operation = "SOMETHING";

        // When
        Exception exception = assertThrows(NotSupportedOperationException.class, () -> OperationType.getForString(operation));

        // Then
        assertTrue(exception.getMessage().contains("Not supported operation: " + operation));
    }

    @Test
    void requireArgumentsOrFail() {
        // Given
        int requiredArguments = 0;

        // When
        assertDoesNotThrow(() -> OperationType.requireArgumentsOrFail(Collections.emptyList(), requiredArguments));

        // Then
    }

    @Test
    void requireArgumentsOrFail_throwsWrongNumberOfArgumentsException() {
        // Given
        int requiredArguments = 2;

        // When
        Exception exception = assertThrows(WrongNumberOfArgumentsException.class, () -> OperationType.requireArgumentsOrFail(Collections.emptyList(), requiredArguments));

        // Then
        assertTrue(exception.getMessage().contains("Wrong number of arguments, required: " + requiredArguments));
    }
}

package com.cbtl.antlr;

import com.cbtl.domain.constraint.ConstraintNode;
import com.cbtl.domain.constraint.ExpressionNode;
import com.cbtl.domain.constraint.FunctionNode;
import com.cbtl.error.MalformedInputException;
import com.cbtl.error.SyntaxErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomProductConstraintVisitorTest {
    CustomProductConstraintVisitor classUnderTest;
    @BeforeEach
    void setUp() {
        this.classUnderTest = new CustomProductConstraintVisitor();
    }

    @Test
    void visitExpressionFunction() {
        // Given
        String operator = "OR";
        String test = String.format("%s('ONE','TWO')", operator);
        ProductConstraintParser.ExpressionContext expressionContext = this.setup(test);

        // When
        ExpressionNode result = this.classUnderTest.visit(expressionContext);

        // Then
        assertTrue(result instanceof FunctionNode);
        FunctionNode castedResult = (FunctionNode) result;
        assertEquals(operator, castedResult.getOperator());
        assertEquals(2, castedResult.getExpressions().size());
        assertTrue(castedResult.getExpressions().stream().allMatch(expressionNode -> expressionNode.getClass().equals(ConstraintNode.class)));
    }

    @Test
    void visitExpressionConstraint() {
        // Given
        String constraintName = "ONLY_FOR_RICH_PEOPLE";
        String test = String.format("'%s'", constraintName);
        ProductConstraintParser.ExpressionContext expressionContext = this.setup(test);

        // When
        ExpressionNode result = this.classUnderTest.visit(expressionContext);

        // Then
        assertTrue(result instanceof ConstraintNode);
        assertEquals(constraintName, ((ConstraintNode) result).getConstraintName());
    }

    @Test
    void should_throw_exception_on_malformed_input() {
        // Given
        String operator = "lowercaseOperator";
        String malformedInput = String.format("%s'ONE',TWO')", operator);

        // When
        Exception exception = assertThrows(MalformedInputException.class, () -> this.setup(malformedInput));

        // Then
        assertTrue(exception.getMessage().contains("Malformed"));
    }

    private ProductConstraintParser.ExpressionContext setup(String input) {
        ProductConstraintLexer constraintsLexer = new ProductConstraintLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(constraintsLexer);
        ProductConstraintParser parser = new ProductConstraintParser(tokens);
        parser.addErrorListener(new SyntaxErrorListener());
        return parser.expression();
    }
}

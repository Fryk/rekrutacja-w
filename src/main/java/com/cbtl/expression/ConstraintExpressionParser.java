package com.cbtl.expression;

import com.cbtl.antlr.CustomProductConstraintVisitor;
import com.cbtl.antlr.ProductConstraintLexer;
import com.cbtl.antlr.ProductConstraintParser;
import com.cbtl.error.SyntaxErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class ConstraintExpressionParser {
    public static ExpressionNode getExpression(String constraintExpression) {
        ProductConstraintLexer constraintsLexer = new ProductConstraintLexer(CharStreams.fromString(constraintExpression));
        CommonTokenStream tokens = new CommonTokenStream(constraintsLexer);

        ProductConstraintParser parser = new ProductConstraintParser(tokens);
        parser.addErrorListener(new SyntaxErrorListener());

        CustomProductConstraintVisitor visitor = new CustomProductConstraintVisitor();

        return visitor.visit(parser.expression());
    }
}

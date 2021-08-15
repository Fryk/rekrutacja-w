package com.cbtl.antlr;

import com.cbtl.expression.ConstraintNode;
import com.cbtl.expression.ExpressionNode;
import com.cbtl.expression.OperationNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class CustomProductConstraintVisitor extends ProductConstraintBaseVisitor<ExpressionNode> {
    private static final Logger logger = LogManager.getLogger(CustomProductConstraintVisitor.class);

    @Override
    public ExpressionNode visitExpressionFunction(ProductConstraintParser.ExpressionFunctionContext ctx) {
        List<ExpressionNode> expressions = ctx.expression().stream().map(this::visit).collect(Collectors.toList());
        return new OperationNode(ctx.OPERATOR().getText(), expressions);
    }

    @Override
    public ExpressionNode visitExpressionConstraint(ProductConstraintParser.ExpressionConstraintContext ctx) {
        final String constraintName = ctx.CONSTRAINT().getText().replace("'", "");
        return new ConstraintNode(constraintName);
    }

    public ExpressionNode visit(ProductConstraintParser.ExpressionContext ctx) {
        if (ctx instanceof ProductConstraintParser.ExpressionConstraintContext constraintContext) {
            return this.visitExpressionConstraint(constraintContext);
        } else if (ctx instanceof ProductConstraintParser.ExpressionFunctionContext functionContext) {
            return this.visitExpressionFunction(functionContext);
        }
        logger.warn("Not supported expression found ({}), skipping", ctx.getClass().getName());
        return null;
    }
}

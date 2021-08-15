package com.cbtl.antlr;

import com.cbtl.domain.constraint.ConstraintNode;
import com.cbtl.domain.constraint.ExpressionNode;
import com.cbtl.domain.constraint.FunctionNode;

import java.util.List;
import java.util.stream.Collectors;

public class CustomProductConstraintVisitor extends ProductConstraintBaseVisitor<ExpressionNode> {
    @Override
    public ExpressionNode visitExpressionFunction(ProductConstraintParser.ExpressionFunctionContext ctx) {
        List<ExpressionNode> expressions = ctx.expression().stream().map(this::visit).collect(Collectors.toList());
        return new FunctionNode(ctx.OPERATOR().getText(), expressions);
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
        return null;
    }
}

package com.cbtl.expression;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.expression.operation.OperationType;
import com.cbtl.expression.helper.Try;
import lombok.Value;

import java.util.List;

@Value
public class OperationNode implements ExpressionNode {
    String operator;
    List<ExpressionNode> expressions;

    @Override
    public Try checkRestrictions(Client client, Product product) {
        return OperationType.getForString(this.operator).apply(client, product, this.expressions);
    }
}

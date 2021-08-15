package com.cbtl.expression;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.expression.util.ConstraintTypeUtil;
import com.cbtl.expression.helper.Try;
import lombok.Value;

@Value
public class ConstraintNode implements ExpressionNode {
    String constraintName;

    @Override
    public Try checkRestrictions(Client client, Product product) {
        return ConstraintTypeUtil.getConstraintForString(this.constraintName).verify(client, product);
    }
}

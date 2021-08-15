package com.cbtl.expression;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.expression.helper.Try;

public interface ExpressionNode {
    Try checkRestrictions(Client client, Product product);
}

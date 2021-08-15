package com.cbtl.domain.constraint;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.helper.Try;

public interface ExpressionNode {
    Try<Boolean> checkRestrictions(Client client, Product product);
}

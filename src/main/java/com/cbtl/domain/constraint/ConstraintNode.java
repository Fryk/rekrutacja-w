package com.cbtl.domain.constraint;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.helper.Try;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
public class ConstraintNode implements ExpressionNode {
    String constraintName;

    @Override
    public Try<Boolean> checkRestrictions(Client client, Product product) {
        return null;
    }
}

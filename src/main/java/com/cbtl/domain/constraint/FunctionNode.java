package com.cbtl.domain.constraint;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.helper.Try;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
public class FunctionNode implements ExpressionNode {
    String operator;
    List<ExpressionNode> expressions;

    @Override
    public Try<Boolean> checkRestrictions(Client client, Product product) {
        return null;
    }
}

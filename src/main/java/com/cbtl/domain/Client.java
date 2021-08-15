package com.cbtl.domain;

import com.cbtl.error.MalformedInputException;
import com.cbtl.error.NotSupportedConstraintException;
import com.cbtl.error.NotSupportedOperationException;
import com.cbtl.error.WrongNumberOfArgumentsException;
import com.cbtl.expression.ConstraintExpressionParser;
import com.cbtl.expression.ExpressionNode;
import com.cbtl.expression.helper.Try;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Client {
    private boolean vip;
    private LocalDate birthDate;
    @Builder.Default
    BigDecimal accountBalance = BigDecimal.ZERO;
    @Builder.Default
    List<Client> relations = new ArrayList<>();

    public Try canPurchase(Product product) {
        ExpressionNode node = ConstraintExpressionParser.getExpression(product.getSpecification().getConstraint());
        try {
            return node.checkRestrictions(this, product);
        } catch (NotSupportedConstraintException | NotSupportedOperationException | WrongNumberOfArgumentsException e) {
            return Try.builder().result(false).reason(e.getMessage()).build();
        }
    }
}

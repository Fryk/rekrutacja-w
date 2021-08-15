package com.cbtl.expression.constraint;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.expression.helper.Try;

import java.math.BigDecimal;
import java.util.Collections;

public class ForRichPeople implements Verifiable {
    private static final BigDecimal WEALTH = new BigDecimal(4000);
    private static final String MESSAGE = "Only for rich people";

    @Override
    public Try verify(Client client, Product product) {
        boolean test = client.getAccountBalance().compareTo(WEALTH) >= 0;
        return new Try(test, MESSAGE, Collections.emptyList());
    }
}

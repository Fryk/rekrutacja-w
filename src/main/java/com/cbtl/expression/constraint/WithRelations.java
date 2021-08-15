package com.cbtl.expression.constraint;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.expression.helper.Try;

import java.util.Collections;

public class WithRelations implements Verifiable {
    private static final String MESSAGE = "Only for people with relations";

    @Override
    public Try verify(Client client, Product product) {
        boolean test =!client.getRelations().isEmpty();
        return new Try(test, MESSAGE, Collections.emptyList());
    }
}

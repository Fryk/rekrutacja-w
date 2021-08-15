package com.cbtl.expression.constraint;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.expression.helper.Try;

import java.util.Collections;

public class VIPOnly implements Verifiable {
    private static final String MESSAGE = "Only for VIPs";

    @Override
    public Try verify(Client client, Product product) {
        boolean test = client.isVip();
        return new Try(test, MESSAGE, Collections.emptyList());
    }
}

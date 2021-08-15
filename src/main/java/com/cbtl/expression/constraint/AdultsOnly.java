package com.cbtl.expression.constraint;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.expression.helper.Try;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;

public class AdultsOnly implements Verifiable {
    private static final int ADULT_AGE = 18;
    private static final String MESSAGE = "Only for rich people";

    @Override
    public Try verify(Client client, Product product) {
        boolean test = Period.between(client.getBirthDate(), LocalDate.now()).getYears() >= ADULT_AGE;
        return new Try(test, MESSAGE, Collections.emptyList());
    }
}

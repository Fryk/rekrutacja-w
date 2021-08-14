package com.cbtl.domain;

import com.cbtl.helper.Try;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Client {
    private boolean vip;
    private LocalDate birthDate;
    BigDecimal accountBalance;
    List<Client> relations;

    Try<Boolean> canPurchase(Product product) {
        return null;
    }
}

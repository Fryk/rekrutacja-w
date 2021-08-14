package com.cbtl.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductSpecification {
    private String constraint;
    private BigDecimal net;
    float tax;
}

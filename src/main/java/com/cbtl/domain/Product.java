package com.cbtl.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.MathContext;

@Data
@Builder
public class Product {
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL32;
    private String name;
    private ProductSpecification specification;

    public BigDecimal getGrossValue() {
        return this.specification.getNet()
                .multiply(BigDecimal.valueOf(this.specification.getTax()), MATH_CONTEXT)
                .divide(BigDecimal.valueOf(100), MATH_CONTEXT)
                .add(this.specification.getNet(), MATH_CONTEXT);
    }
}

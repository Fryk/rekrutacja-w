package com.cbtl.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void getGrossValue() {
        // Given
        ProductSpecification specification = ProductSpecification.builder()
                .net(BigDecimal.TEN)
                .tax(23F)
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(specification)
                .build();

        // When
        BigDecimal result = product.getGrossValue();

        // Then
        float expectedResult = 10 * 1.23F;
        assertEquals(new BigDecimal(expectedResult, MathContext.DECIMAL32), result);
    }
}

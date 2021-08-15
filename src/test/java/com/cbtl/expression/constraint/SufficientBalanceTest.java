package com.cbtl.expression.constraint;

import com.cbtl.domain.Client;
import com.cbtl.domain.Product;
import com.cbtl.domain.ProductSpecification;
import com.cbtl.expression.helper.Try;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SufficientBalanceTest {
    public final SufficientBalance classUnderTest = new SufficientBalance();

    @Test
    void verify_valid() {
        // given
        Client client = Client.builder().accountBalance(new BigDecimal(4000)).build();
        ProductSpecification productSpecification = ProductSpecification.builder().net(BigDecimal.TEN).tax(5f).build();
        Product product = Product.builder().specification(productSpecification).build();

        // When
        Try result = this.classUnderTest.verify(client, product);

        // Then
        assertTrue(result.getResult());
    }

    @Test
    void verify_notValid() {
        // given
        Client client = Client.builder().accountBalance(BigDecimal.ZERO).build();
        ProductSpecification productSpecification = ProductSpecification.builder().net(BigDecimal.TEN).tax(5f).build();
        Product product = Product.builder().specification(productSpecification).build();

        // When
        Try result = this.classUnderTest.verify(client, product);

        // Then
        assertFalse(result.getResult());
    }
}

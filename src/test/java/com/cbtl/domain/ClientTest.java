package com.cbtl.domain;

import com.cbtl.error.MalformedInputException;
import com.cbtl.expression.helper.Try;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    public static Client classUnderTest;

    @BeforeAll
    static void setup() {
        classUnderTest = Client.builder()
                .vip(true)
                .birthDate(LocalDate.of(2000, 1, 1))
                .accountBalance(new BigDecimal(3000))
                .build();
    }

    @Test
    void canPurchase_constraintValid() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(2000))
                .tax(23f)
                .constraint("'SUFFICIENT_BALANCE'")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertTrue(result.getResult());
        assertEquals(0, result.getChildTries().size());
    }

    @Test
    void canPurchase_constraintNotValid() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(3000))
                .tax(23f)
                .constraint("'SUFFICIENT_BALANCE'")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertFalse(result.getResult());
        assertTrue(result.getReason().contains("Only for people with sufficient balance"));
        assertEquals(0, result.getChildTries().size());
    }

    @Test
    void canPurchase_reverseConstraintValid() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(3000))
                .tax(23f)
                .constraint("NOT('SUFFICIENT_BALANCE')")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertTrue(result.getResult());
        assertEquals(1, result.getChildTries().size());
        assertFalse(result.getChildTries().get(0).getResult());
    }

    @Test
    void canPurchase_andOperationValid() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(2000))
                .tax(23f)
                .constraint("AND('SUFFICIENT_BALANCE','VIP_ONLY')")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertTrue(result.getResult());
        assertEquals(2, result.getChildTries().size());
        assertTrue(result.getChildTries().get(0).getResult());
        assertTrue(result.getChildTries().get(1).getResult());
    }

    @Test
    void canPurchase_andOperationNotValid() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(2000))
                .tax(23f)
                .constraint("AND('SUFFICIENT_BALANCE','WITH_RELATIONS')")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertFalse(result.getResult());
        assertEquals(2, result.getChildTries().size());
        assertTrue(result.getChildTries().get(0).getResult());
        assertFalse(result.getChildTries().get(1).getResult());
    }

    @Test
    void canPurchase_orOperationValid() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(2000))
                .tax(23f)
                .constraint("OR('SUFFICIENT_BALANCE','WITH_RELATIONS')")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertTrue(result.getResult());
        assertEquals(2, result.getChildTries().size());
        assertTrue(result.getChildTries().get(0).getResult());
        assertFalse(result.getChildTries().get(1).getResult());
    }

    @Test
    void canPurchase_orOperationNotValid() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(3000))
                .tax(23f)
                .constraint("OR('SUFFICIENT_BALANCE','WITH_RELATIONS')")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertFalse(result.getResult());
        assertEquals(2, result.getChildTries().size());
        assertFalse(result.getChildTries().get(0).getResult());
        assertFalse(result.getChildTries().get(1).getResult());
    }

    @Test
    void canPurchase_exerciseGoal() {
        // Given
        Client client = Client.builder()
                .vip(true)
                .birthDate(LocalDate.of(1970, 1, 20))
                .accountBalance(BigDecimal.valueOf(500f))
                .relations(Collections.emptyList())
                .build();
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(BigDecimal.valueOf(50.1f))
                .tax(23f)
                .constraint("OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertTrue(result.getResult()); // OR
        assertEquals(2, result.getChildTries().size());
        assertTrue(result.getChildTries().get(0).getResult());  // VIP_ONLY
        assertFalse(result.getChildTries().get(1).getResult()); // AND
        assertTrue(result.getChildTries().get(1).getChildTries().get(0).getResult()); // NOT
        assertFalse(result.getChildTries().get(1).getChildTries().get(1).getResult()); // WITH_RELATIONS
        assertFalse(result.getChildTries().get(1).getChildTries().get(0).getChildTries().get(0).getResult()); // FOR_RICH_PEOPLE
    }

    @Test
    void canPurchase_throwsMalformedInputException() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(3000))
                .tax(23f)
                .constraint("CallMeMaybe")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Exception exception = assertThrows(MalformedInputException.class, () -> classUnderTest.canPurchase(product));

        // Then
        assertTrue(exception.getMessage().contains("Malformed"));
    }

    @Test
    void canPurchase_shouldFailForUnknownOperation() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(3000))
                .tax(23f)
                .constraint("XOR('SUFFICIENT_BALANCE','WITH_RELATIONS')")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertFalse(result.getResult());
        assertTrue(result.getReason().contains("Not supported operation"));
    }

    @Test
    void canPurchase_shouldFailForUnknownConstraint() {
        // Given
        ProductSpecification productSpecification = ProductSpecification.builder()
                .net(new BigDecimal(3000))
                .tax(23f)
                .constraint("AND('GOD_MODE','VIP_ONLY')")
                .build();
        Product product = Product.builder()
                .name("Test product")
                .specification(productSpecification)
                .build();

        // When
        Try result = classUnderTest.canPurchase(product);

        // Then
        assertFalse(result.getResult());
        assertTrue(result.getReason().contains("Not supported constraint"));
    }
}

package com.cbtl.expression.constraint;

import com.cbtl.domain.Client;
import com.cbtl.expression.helper.Try;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.cbtl.expression.constraint.ConstraintTestUtil.DUMMY_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;

class AdultsOnlyTest {
    public final AdultsOnly classUnderTest = new AdultsOnly();

    @Test
    void verify_valid() {
        // given
        Client client = Client.builder().birthDate(LocalDate.of(1990, 1, 6)).build();

        // When
        Try result = this.classUnderTest.verify(client, DUMMY_PRODUCT);

        // Then
        assertTrue(result.getResult());
    }

    @Test
    void verify_notValid() {
        // given
        Client client = Client.builder().birthDate(LocalDate.of(2010, 1, 6)).build();

        // When
        Try result = this.classUnderTest.verify(client, DUMMY_PRODUCT);

        // Then
        assertFalse(result.getResult());
    }
}

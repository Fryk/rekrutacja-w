package com.cbtl.expression.constraint;

import com.cbtl.domain.Client;
import com.cbtl.expression.helper.Try;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.cbtl.expression.constraint.ConstraintTestUtil.DUMMY_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;

class VIPOnlyTest {
    public final VIPOnly classUnderTest = new VIPOnly();

    @Test
    void verify_valid() {
        // given
        Client client = Client.builder().vip(true).build();

        // When
        Try result = this.classUnderTest.verify(client, DUMMY_PRODUCT);

        // Then
        assertTrue(result.getResult());
    }

    @Test
    void verify_notValid() {
        // given
        Client client = Client.builder().vip(false).build();

        // When
        Try result = this.classUnderTest.verify(client, DUMMY_PRODUCT);

        // Then
        assertFalse(result.getResult());
    }
}

package com.cbtl.expression.constraint;

import com.cbtl.domain.Client;
import com.cbtl.expression.helper.Try;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.cbtl.expression.constraint.ConstraintTestUtil.DUMMY_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;

class ForRichPeopleTest {
    public final ForRichPeople classUnderTest = new ForRichPeople();

    @Test
    void verify_valid() {
        // given
        Client client = Client.builder().accountBalance(new BigDecimal(4000)).build();

        // When
        Try result = this.classUnderTest.verify(client, DUMMY_PRODUCT);

        // Then
        assertTrue(result.getResult());
    }

    @Test
    void verify_notValid() {
        // given
        Client client = Client.builder().accountBalance(new BigDecimal(3999)).build();

        // When
        Try result = this.classUnderTest.verify(client, DUMMY_PRODUCT);

        // Then
        assertFalse(result.getResult());
    }
}

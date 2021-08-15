package com.cbtl.expression.constraint;

import com.cbtl.domain.Client;
import com.cbtl.expression.helper.Try;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.cbtl.expression.constraint.ConstraintTestUtil.DUMMY_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;

class WithRelationsTest {
    public final WithRelations classUnderTest = new WithRelations();

    @Test
    void verify_valid() {
        // given
        Client rel1 = Client.builder().build();
        Client client = Client.builder().relations(List.of(rel1)).build();

        // When
        Try result = this.classUnderTest.verify(client, DUMMY_PRODUCT);

        // Then
        assertTrue(result.getResult());
    }

    @Test
    void verify_notValid() {
        // given
        Client client = Client.builder().build();

        // When
        Try result = this.classUnderTest.verify(client, DUMMY_PRODUCT);

        // Then
        assertFalse(result.getResult());
    }
}

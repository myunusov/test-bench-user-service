package ru.iunusov.testbench.users.domain;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import org.junit.jupiter.api.Test;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

class DepartmentTest {

    @Test public void checkMyClassIsImmutable() {
        assertImmutable(Department.class);
    }

    @Test
    public void testToString() {
        ToStringVerifier.forClass(Department.class)
                .withClassName(NameStyle.SIMPLE_NAME)
                .verify();
    }

}
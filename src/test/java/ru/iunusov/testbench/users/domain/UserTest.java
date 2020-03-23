package ru.iunusov.testbench.users.domain;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  public void testToString() {
    ToStringVerifier.forClass(User.class)
            .withClassName(NameStyle.SIMPLE_NAME)
            .verify();
  }
}

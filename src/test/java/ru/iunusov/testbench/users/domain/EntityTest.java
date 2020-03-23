package ru.iunusov.testbench.users.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntityTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(Entity.class)
        .withNonnullFields("id")
        .withIgnoredFields("name")
        .verify();
  }

  @Test
  public void createWithNullId() {
    Assertions.assertThrows(NullPointerException.class, () -> new Entity(null, "name") {});
  }

  @Test
  public void createWithNullName() {
    Assertions.assertThrows(NullPointerException.class, () -> new Entity("id", null) {});
  }
}

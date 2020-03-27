package ru.iunusov.testbench.users.domain;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

class DepartmentTest {

  @Test
  public void checkMyClassIsImmutable() {
    assertImmutable(Department.class);
  }

  @Test
  public void equalsContract() {
      EqualsVerifier.forClass(Department.class).verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(Department.class).withClassName(NameStyle.SIMPLE_NAME).verify();
  }

  @Test
  public void testSuccessCreate() {
    Department department = new Department("it", "1");
    Assert.assertEquals("it", department.getName());
    Assert.assertEquals("1", department.getManagerId());
  }

  @Test
  public void testWithoutName() {
    assertThrows(NullPointerException.class, () -> new Department(null, "1"));
  }

  @Test
  public void testWithoutManager() {
    assertThrows(NullPointerException.class, () -> new Department("it", null));
  }
}

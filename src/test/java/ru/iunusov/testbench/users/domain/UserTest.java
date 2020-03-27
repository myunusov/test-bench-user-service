package ru.iunusov.testbench.users.domain;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

  @Test
  public void testToString() {
    ToStringVerifier.forClass(User.class)
            .withClassName(NameStyle.SIMPLE_NAME)
            .verify();
  }

  @Test
  public void testWithoutId() {
    assertThrows(NullPointerException.class, () ->
            User.builder()
            .name("name")
            .firstName("Ivan")
            .lastName("Ivanov")
            .email("name@mail.com")
            .department(new Department("it", "1"))
            .build());
  }

  @Test
  public void testWithoutFirstName() {
    assertThrows(NullPointerException.class, () ->
            User.builder()
                    .id("1")
                    .name("name")
                    .lastName("Ivanov")
                    .email("name@mail.com")
                    .department(new Department("it", "1"))
                    .build());

  }  @Test
  public void testWithoutLastName() {
    assertThrows(NullPointerException.class, () ->
            User.builder()
                    .id("1")
                    .name("name")
                    .firstName("Ivan")
                    .email("name@mail.com")
                    .department(new Department("it", "1"))
                    .build());

  }  @Test
  public void testWithoutDepartment() {
    assertThrows(NullPointerException.class, () ->
            User.builder()
                    .id("1")
                    .name("name")
                    .firstName("Ivan")
                    .lastName("Ivanov")
                    .email("name@mail.com")
                    .build());

  }

  @Test
  public void testSuccessCreate() {
    User user = User.builder()
            .id("1")
            .name("name")
            .firstName("Ivan")
            .lastName("Ivanov")
            .email("name@mail.com")
            .department(new Department("it", "1"))
            .build();
    Assert.assertEquals("1", user.getId());
    Assert.assertEquals("name", user.getName());
    Assert.assertEquals("Ivan", user.getFirstName());
    Assert.assertEquals("Ivanov", user.getLastName());
    Assert.assertEquals("name@mail.com", user.getEmail());
    Assert.assertEquals("1", user.getDepartment().getManagerId());
    Assert.assertEquals("it", user.getDepartment().getName());

  }

}

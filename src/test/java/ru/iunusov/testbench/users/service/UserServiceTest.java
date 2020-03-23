package ru.iunusov.testbench.users.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iunusov.testbench.users.config.ServiceTestConfiguration;
import ru.iunusov.testbench.users.domain.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(value = {"spring.main.allow-bean-definition-overriding=true"}, classes = ServiceTestConfiguration.class)
class UserServiceTest {

  @Autowired
  private UserService service;

  @Test
  void findAllUsers() {
    final List<User> users = service.findAllUsers();
    Assert.assertEquals(1, users.size());
    Assert.assertEquals("1", users.get(0).getId());
    Assert.assertEquals("name", users.get(0).getName());
    Assert.assertEquals("name@mail.com", users.get(0).getEmail());
  }

  @Test
  void getUserById() {
    final User user = service.getUserBy("1");
    Assert.assertEquals("1", user.getId());
    Assert.assertEquals("name", user.getName());
    Assert.assertEquals("name@mail.com", user.getEmail());
  }

  @Test
  void getUserByUnknownId() {
    assertThrows(NotFoundException.class, () -> service.getUserBy("2"));
  }

  @Test
  void getUserWithNullId() {
    assertThrows(NullPointerException.class, () -> service.getUserBy(null));
  }

  @Test
  void getUserWithInvalidId() {
    assertThrows(UncategorizedSQLException.class, () -> service.getUserBy("?"));
  }
}
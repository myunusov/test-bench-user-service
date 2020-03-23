package ru.iunusov.testbench.users;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.iunusov.testbench.users.config.ServiceTestConfiguration;

@SpringBootTest(
        value = {"spring.main.allow-bean-definition-overriding=true"},
        classes = {UserApplication.class, ServiceTestConfiguration.class}
        )
@ActiveProfiles("test")
class UserApplicationTests {

  @Test
  void contextLoads() {}
}

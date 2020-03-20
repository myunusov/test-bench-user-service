package ru.iunusov.testbench.users;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import ru.iunusov.testbench.users.config.ServiceTestConfiguration;
import ru.iunusov.testbench.users.domain.User;
import ru.iunusov.testbench.users.service.UserService;
import ru.iunusov.testbench.users.web.UserController;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(value = {"spring.main.allow-bean-definition-overriding=true"}, classes = ServiceTestConfiguration.class)
@ActiveProfiles("test")
public abstract class BaseClassForContractTest {

  @Autowired
  UserController controller;

  @MockBean
  UserService service;

  @Before
  public void setup() {
    RestAssuredMockMvc.standaloneSetup(controller);
      when(service.findAllUsers())
              .thenReturn(singletonList(new User("id", "name", "name@mail.com")));
  }
}

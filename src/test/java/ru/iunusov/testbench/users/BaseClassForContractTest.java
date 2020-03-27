package ru.iunusov.testbench.users;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.iunusov.testbench.users.config.ServiceTestConfiguration;
import ru.iunusov.testbench.users.domain.Department;
import ru.iunusov.testbench.users.domain.User;
import ru.iunusov.testbench.users.service.NotFoundException;
import ru.iunusov.testbench.users.service.UserService;
import ru.iunusov.testbench.users.web.ErrorAdvice;
import ru.iunusov.testbench.users.web.UserController;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(
    value = {"spring.main.allow-bean-definition-overriding=true"},
    classes = ServiceTestConfiguration.class)
@ActiveProfiles("test")
public abstract class BaseClassForContractTest {

  public static final User FAKE_USER = User.builder()
          .id("1")
          .name("name")
          .firstName("Ivan")
          .lastName("Ivanov")
          .email("name@mail.com")
          .department(new Department("department", "1"))
          .build();

  @Autowired UserController controller;

  @Autowired ErrorAdvice controllerAdvice;

  @MockBean UserService service;

  @Before
  public void setup() {
    RestAssuredMockMvc
            .standaloneSetup(
                    MockMvcBuilders
                    .standaloneSetup(controller)
                    .setControllerAdvice(controllerAdvice));

    when(service.findAllUsers()).thenReturn(singletonList(FAKE_USER));
    when(service.getUserBy("1")).thenReturn(FAKE_USER);
    when(service.getUserBy("2")).thenThrow(new NotFoundException("User '2' is not found"));
  }


}

package ru.iunusov.testbench.users.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.iunusov.testbench.users.service.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class UserControllerTests {

  @MockBean
  private UserService service;

  @Autowired UserController controller;

  @Autowired ErrorAdvice controllerAdvice;

  @Test
  public void checkInternalError() throws Exception {
    final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(controllerAdvice)
            .build();
    when(service.findAllUsers()).thenThrow(new IllegalStateException("Unknown Error"));
    mockMvc
        .perform(get("/users"))
        .andDo(print())
        .andExpect(status().isInternalServerError());
  }
}

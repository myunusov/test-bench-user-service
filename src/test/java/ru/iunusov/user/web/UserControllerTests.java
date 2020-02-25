package ru.iunusov.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.iunusov.user.domain.User;
import ru.iunusov.user.service.UserService;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class UserControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean
  private UserService service;

  @Test
  public void users() throws Exception {
    when(service.findAllUsers()).thenReturn(singletonList(new User("id", "name", "name@mail.com")));
    mockMvc
        .perform(get("/users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is("id")))
        .andExpect(jsonPath("$[0].name", is("name")))
        .andExpect(jsonPath("$[0].email", is("name@mail.com")));
  }
}

package ru.iunusov.user.web;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class UserControllerTests {

  @Autowired private MockMvc mockMvc;

  @Test
  public void users() throws Exception {
    mockMvc
        .perform(get("/users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("1"));
  }
}

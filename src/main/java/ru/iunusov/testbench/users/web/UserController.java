package ru.iunusov.testbench.users.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.iunusov.testbench.users.domain.User;
import ru.iunusov.testbench.users.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @GetMapping("/users")
  public List<User> getUsers() {
    return service.findAllUsers();
  }

  @GetMapping("/users/{id}")
  public User getUser(@PathVariable final String id) {
    return service.getUserBy(id);
  }
}

package ru.iunusov.testbench.users.web;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iunusov.testbench.users.domain.User;
import ru.iunusov.testbench.users.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @GetMapping("/users")
  public List<User> getUsers() {
    return service.findAllUsers();
  }
}

package ru.iunusov.user.web;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iunusov.user.domain.User;
import ru.iunusov.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @GetMapping("/users")
  public List<User> getUsers() {
    return service.findAllUsers();
  }
}

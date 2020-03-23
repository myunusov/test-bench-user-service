package ru.iunusov.testbench.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iunusov.testbench.users.domain.User;
import ru.iunusov.testbench.users.persistence.UserRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  @Transactional
  public List<User> findAllUsers() {
    return repository.users();
  }

  @Transactional
  public User getUserBy(String id) {
    return repository.user(id).orElseThrow(() -> new NotFoundException(
            format("User '%s' is not found", id)
    ));
  }
}

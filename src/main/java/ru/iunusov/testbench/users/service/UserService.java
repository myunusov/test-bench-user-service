package ru.iunusov.testbench.users.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iunusov.testbench.users.domain.User;
import ru.iunusov.testbench.users.persistence.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  @Transactional
  public List<User> findAllUsers() {
    return repository.users();
  }

}

package ru.iunusov.user.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iunusov.user.domain.User;
import ru.iunusov.user.persistence.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  @Transactional("transactionManager")
  public List<User> findAllUsers() {
    return repository.users();
  }

}

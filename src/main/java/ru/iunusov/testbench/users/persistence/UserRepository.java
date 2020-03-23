package ru.iunusov.testbench.users.persistence;

import ru.iunusov.testbench.users.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  List<User> users();

  Optional<User> user(String id);
}

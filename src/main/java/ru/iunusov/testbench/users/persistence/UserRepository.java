package ru.iunusov.testbench.users.persistence;

import java.util.List;

import ru.iunusov.testbench.users.domain.User;

public interface UserRepository {
  List<User> users();
}

package ru.iunusov.user.persistence;

import java.util.List;

import ru.iunusov.user.domain.User;

public interface UserRepository {
  List<User> users();
}

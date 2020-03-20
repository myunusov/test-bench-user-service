package ru.iunusov.testbench.users.persistence;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.iunusov.testbench.users.domain.User;
import ru.iunusov.testbench.users.db.tables.pojos.Users;

import static java.util.stream.Collectors.toList;
import static ru.iunusov.testbench.users.db.Tables.USERS;


@Service
@RequiredArgsConstructor
public class UserRepositoryDefault implements UserRepository {

  private final DSLContext dsl;

  @Override
  public List<User> users() {
    final List<Users> result = dsl.select(USERS.ID, USERS.NAME, USERS.EMAIL).from(USERS).fetch().into(Users.class);
    return result.stream().map(it -> new User(it.getId(), it.getName(), it.getEmail())).collect(toList()) ;
  }
}

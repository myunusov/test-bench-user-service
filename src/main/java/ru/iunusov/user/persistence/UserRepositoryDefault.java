package ru.iunusov.user.persistence;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.iunusov.db.tables.pojos.Users;
import ru.iunusov.user.domain.User;
import static java.util.stream.Collectors.toList;
import static ru.iunusov.db.tables.Users.USERS;

@Service
@RequiredArgsConstructor
public class UserRepositoryDefault implements UserRepository {

  private final DSLContext dsl;

  @Override
  public List<User> users() {
    final List<Users> result = dsl.select(USERS.ID, USERS.NAME).from(USERS).fetch().into(Users.class);
    return result.stream().map(it -> new User(it.getId(), it.getName(), it.getEmail())).collect(toList()) ;
  }
}

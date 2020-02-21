package ru.iunusov.user.persistence;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.iunusov.user.domain.User;
import static ru.iunusov.db.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class UserRepositoryDefault implements UserRepository {

  private final DSLContext dsl;

  @Override
  public List<User> users() {
    return dsl.select(USERS.ID, USERS.NAME).from(USERS).fetch().into(User.class);
  }
}

package ru.iunusov.testbench.users.persistence;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.iunusov.testbench.users.db.tables.pojos.Users;
import ru.iunusov.testbench.users.domain.User;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static ru.iunusov.testbench.users.db.Tables.USERS;


@Repository
@RequiredArgsConstructor
public class UserRepositoryDefault implements UserRepository {

  private final DSLContext dsl;

  @Override
  public List<User> users() {
    return dsl
            .select(USERS.ID, USERS.NAME, USERS.EMAIL)
            .from(USERS)
            .fetch().into(Users.class).stream()
            .map(it -> new User(it.getId(), it.getName(), it.getEmail()))
            .collect(toList()) ;
  }

  @Override
  public Optional<User> user(@NonNull String id) {
    return dsl.select(USERS.ID, USERS.NAME, USERS.EMAIL)
        .from(USERS)
        .where(USERS.ID.equal(id))
        .fetchOptional()
        .map(it -> new User(it.value1(), it.value2(), it.value3()));
  }
}

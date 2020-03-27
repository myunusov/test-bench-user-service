package ru.iunusov.testbench.users.persistence;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record8;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;
import ru.iunusov.testbench.users.db.tables.records.DepartmentsRecord;
import ru.iunusov.testbench.users.db.tables.records.UsersRecord;
import ru.iunusov.testbench.users.domain.Department;
import ru.iunusov.testbench.users.domain.User;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static ru.iunusov.testbench.users.db.Tables.DEPARTMENTS;
import static ru.iunusov.testbench.users.db.Tables.USERS;

@Repository
@RequiredArgsConstructor
public class UserRepositoryDefault implements UserRepository {

  private final DSLContext dsl;

  @Override
  public List<User> users() {
    return selectUsers().fetch().stream().map(this::map).collect(toList());
  }

  @Override
  public Optional<User> user(final @NonNull String id) {
    return selectUsers().where(USERS.ID.equal(id)).fetchOptional().map(this::map);
  }

  private SelectJoinStep<Record8<String, String, String, String, String, String, String, String>> selectUsers() {
    return dsl.select(
            USERS.ID,
            USERS.NAME,
            USERS.FIRST_NAME,
            USERS.LAST_NAME,
            USERS.MIDDLE_NAME,
            USERS.EMAIL,
            DEPARTMENTS.NAME,
            DEPARTMENTS.MANAGER_ID)
        .from(USERS)
        .leftJoin(DEPARTMENTS)
        .on(DEPARTMENTS.ID.eq(USERS.DEPARTMENT_ID));
  }

  @Contract("_ -> new")
  @NotNull
  private User map(@NotNull Record8<String, String, String, String, String, String, String, String> record) {
    final UsersRecord users = record.into(USERS);
    final DepartmentsRecord departments = record.into(DEPARTMENTS);
    return User.builder()
        .id(users.getId())
        .name(users.getName())
        .firstName(users.getFirstName())
        .lastName(users.getLastName())
        .middleName(users.getMiddleName())
        .email(users.getEmail())
        .department(new Department(departments.getName(), departments.getManagerId()))
        .build();
  }
}

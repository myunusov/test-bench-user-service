package ru.iunusov.testbench.users.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User extends Entity {

  private final String email;

  public User(final String id, final String name, final String email) {
    super(id, name);
    this.email = email;
  }

}

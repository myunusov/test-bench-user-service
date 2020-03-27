package ru.iunusov.testbench.users.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@ToString(callSuper=true)
@Schema(description = "The System's user")
public class User extends Entity {

  @Schema(title = "Unique email of user", example = "user@mail.com")
  @UniqueElements
  private final String email;

  public User(final String id, final String name, final String email) {
    super(id, name);
    this.email = email;
  }

}

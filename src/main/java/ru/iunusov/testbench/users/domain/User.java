package ru.iunusov.testbench.users.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter

@ToString(callSuper = true)
@Schema(description = "The System's user")
public class User extends Entity {

  @NonNull
  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(title = "First name of user")
  private final String firstName;

  @NonNull
  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(title = "Last name of user")
  private final String lastName;

  @Size(min = 1, max = 255)
  @Schema(title = "Middle name of user")
  private final String middleName;

  @NonNull
  private Department department;

  @Size(min = 1, max = 255)
  @Schema(title = "Unique email of user", example = "user@mail.com")
  private final String email;

  @Builder
  private User(
      @NonNull final String id,
      @NonNull final String name,
      @NonNull final String firstName,
      @NonNull final String lastName,
      final String middleName,
      @NonNull final Department department,
      final String email) {
    super(id, name);
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.department = department;
    this.email = email;
  }
}

package ru.iunusov.testbench.users.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class Entity {

  @NonNull
  @NotBlank
  @Size(min = 3, max = 36)
  @Schema(title = "Unique identifier of resource")
  private final String id;

  @NonNull
  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(title = "Unique name of resource")
  private final String name;

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Entity)) {
      return false;
    }
    final Entity entity = (Entity) o;
    return id.equals(entity.id);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(id);
  }
}

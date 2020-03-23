package ru.iunusov.testbench.users.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public abstract class Entity {

  @NonNull
  private final String id;
  @NonNull
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

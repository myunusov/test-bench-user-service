package ru.iunusov.testbench.users.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Schema(title = "Department")
public class Department {

    @NonNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Schema(title = "Unique name of department")
    String name;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 36)
    @Schema(title = "Department manager id")
    String managerId;

}

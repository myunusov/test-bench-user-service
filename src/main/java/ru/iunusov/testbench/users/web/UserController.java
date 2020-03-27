package ru.iunusov.testbench.users.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.iunusov.testbench.users.domain.User;
import ru.iunusov.testbench.users.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "user", description = "the User API")
public class UserController {

  private final UserService service;

  @Operation(summary = "Get all users", description = "Returns all users", tags = { "user" })
  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  public List<User> getUsers() {
    return service.findAllUsers();
  }

  @Operation(summary = "Find user by ID", description = "Returns a single user", tags = { "user" })
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successful Operation",
                  content = @Content(schema = @Schema(implementation = User.class))),
          @ApiResponse(responseCode = "404", description = "User not found") })
  @GetMapping("/users/{id}")
  @ResponseStatus(HttpStatus.OK)
  public User getUser(
          @Parameter(description="Id of the user to be obtained.", required=true)
          @PathVariable final String id
  ) {
    return service.getUserBy(id);
  }
}

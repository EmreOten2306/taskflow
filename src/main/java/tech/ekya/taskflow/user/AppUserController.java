package tech.ekya.taskflow.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.task.dto.TaskResponse;
import tech.ekya.taskflow.user.dto.AppUserResponse;
import tech.ekya.taskflow.user.dto.CreateAppUserRequest;
import tech.ekya.taskflow.user.dto.UpdateAppUserRequest;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Users",
        description = "User management operations"
)
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Operation(
            summary = "Create a user",
            description = "Creates a new user"
    )
    @ApiResponse(responseCode = "200", description = "User created successfully")
    @ApiResponse(responseCode = "409", description = "User already exists")
    @PostMapping
    public AppUserResponse createAppUser(
            @Valid @RequestBody CreateAppUserRequest request
    ) {
        return appUserService.createAppUser(request);
    }

    @Operation(
            summary = "Get all users",
            description = "Returns all users"
    )
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    @GetMapping
    public List<AppUserResponse> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @Operation(
            summary = "Get user by ID",
            description = "Returns a user by their ID"
    )
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public AppUserResponse getUserById(@PathVariable Long id) {
        return appUserService.getUserById(id);
    }

    @Operation(
            summary = "Get user's tasks",
            description = "Returns all tasks assigned to a user"
    )
    @ApiResponse(responseCode = "200", description = "User tasks retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}/tasks")
    public List<TaskResponse> getTasksByUserId(@PathVariable Long id) {
        return appUserService.getUserTasks(id);
    }

    @Operation(
            summary = "Update a user",
            description = "Updates an existing user by their ID"
    )
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/{id}")
    public AppUserResponse updateUser(
            @PathVariable Long id,
            @RequestBody UpdateAppUserRequest request
    ) {
        return appUserService.updateUser(id, request);
    }

    @Operation(
            summary = "Delete a user",
            description = "Deletes a user by their ID"
    )
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        appUserService.deleteUserById(id);
    }
}

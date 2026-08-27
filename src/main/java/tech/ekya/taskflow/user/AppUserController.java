package tech.ekya.taskflow.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.task.Task;
import tech.ekya.taskflow.task.dto.TaskResponse;
import tech.ekya.taskflow.user.dto.AppUserResponse;
import tech.ekya.taskflow.user.dto.CreateAppUserRequest;
import tech.ekya.taskflow.user.dto.UpdateAppUserRequest;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;
    public AppUserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    @PostMapping
    public AppUserResponse createAppUser(@Valid @RequestBody CreateAppUserRequest request){

        return appUserService.createAppUser(request);
    }

    @GetMapping
    public List<AppUserResponse> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping ("/{id}")
    public AppUserResponse getUserById(@PathVariable Long id){
        return appUserService.getUserById(id);
    }
    @GetMapping("/{id}/tasks")
    public List<TaskResponse> getTasksByUserId(@PathVariable Long id) {
        return appUserService.getUserTasks(id);
    }


    @PutMapping ("/{id}")
    public AppUserResponse updateUser(@PathVariable Long id,
                                      @RequestBody UpdateAppUserRequest request){
        return appUserService.updateUser(id, request);
    }

    @DeleteMapping ("/{id}")
    public void deleteUserById(@PathVariable Long id){
        appUserService.deleteUserById(id);
    }
}

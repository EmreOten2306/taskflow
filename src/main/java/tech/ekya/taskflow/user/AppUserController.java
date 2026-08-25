package tech.ekya.taskflow.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;
    public AppUserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping ("/{id}")
    public AppUser getUserById(@PathVariable Long id){
        return appUserService.getUserById(id);
    }
    @PutMapping ("/{id}")
    public AppUser updateUser(@PathVariable Long id,
                              @RequestBody AppUser appUser){
        return appUserService.updateUser(id, appUser);
    }

    @DeleteMapping ("/{id}")
    public void deleteUserById(@PathVariable Long id){
        appUserService.deleteUserById(id);
    }
}

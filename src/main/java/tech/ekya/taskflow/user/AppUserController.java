package tech.ekya.taskflow.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppUserController {

    private final AppUserService appUserService;
    public AppUserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }
    @GetMapping("/api/users")
    public List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }
}

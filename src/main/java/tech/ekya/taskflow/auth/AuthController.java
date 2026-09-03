package tech.ekya.taskflow.auth;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ekya.taskflow.auth.dto.RegisterRequest;

@RestController
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

@PostMapping("/api/auth/register")
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
}


}

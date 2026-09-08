package tech.ekya.taskflow.auth;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ekya.taskflow.auth.dto.LoginRequest;
import tech.ekya.taskflow.auth.dto.LoginResponse;
import tech.ekya.taskflow.auth.dto.RegisterRequest;

@RestController
@Tag(
        name = "Authentication",
        description = "User registration and login operations"
)
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account"
    )
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @ApiResponse(responseCode = "409", description = "User already exists")
    @PostMapping("/api/auth/register")
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
}

    @Operation(
            summary = "Login user",
            description = "Authenticates the user and returns a JWT token"
    )
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Invalid email or password")
    @PostMapping ("/api/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
       return authService.login(request);
}

}

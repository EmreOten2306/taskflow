package tech.ekya.taskflow.auth;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.auth.dto.LoginRequest;
import tech.ekya.taskflow.auth.dto.LoginResponse;
import tech.ekya.taskflow.auth.dto.RegisterRequest;
import tech.ekya.taskflow.exception.DuplicateResourceException;
import tech.ekya.taskflow.exception.UnauthorizedException;
import tech.ekya.taskflow.security.JwtService;
import tech.ekya.taskflow.user.AppUser;
import tech.ekya.taskflow.user.AppUserRepository;
import tech.ekya.taskflow.user.Role;

@Transactional
@Service
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(AppUserRepository appUserRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "Email already exists: " + request.email()
            );
        }
        String passwordHash = passwordEncoder.encode(request.password());
        AppUser user = new AppUser();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPasswordHash(passwordHash);
        user.setRole(Role.MEMBER);
        appUserRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Email or password is not found"));

            if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
                throw new UnauthorizedException("Email or password is not found");
            }
            String token = jwtService.generateToken(user);
             return new LoginResponse(token, 3600);


    }

}

package tech.ekya.taskflow.user;

import org.springframework.stereotype.Component;
import tech.ekya.taskflow.user.dto.AppUserResponse;
import tech.ekya.taskflow.user.dto.CreateAppUserRequest;
import tech.ekya.taskflow.user.dto.UpdateAppUserRequest;

@Component
public class AppUserMapper {

    public AppUser toEntity(CreateAppUserRequest request) {
        AppUser appUser = new AppUser();

        appUser.setFullName(request.fullName());
        appUser.setEmail(request.email());
        appUser.setRole(request.role());

        return appUser;
    }

    public void updateEntity(UpdateAppUserRequest request, AppUser appUser) {
        appUser.setFullName(request.fullName());
        appUser.setEmail(request.email());
        appUser.setRole(request.role());
    }


    public AppUserResponse toResponse(AppUser appUser) {
        return new AppUserResponse(
                appUser.getId(),
                appUser.getFullName(),
                appUser.getEmail(),
                appUser.getRole()
        );
    }
}
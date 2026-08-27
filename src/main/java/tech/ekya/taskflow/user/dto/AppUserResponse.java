package tech.ekya.taskflow.user.dto;

import tech.ekya.taskflow.user.Role;

public record AppUserResponse(
        Long id,
        String fullName,
        String email,
        Role role


) {

}

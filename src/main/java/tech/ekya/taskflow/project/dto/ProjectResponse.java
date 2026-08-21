package tech.ekya.taskflow.project.dto;
import tech.ekya.taskflow.project.ProjectStatus;

import java.time.LocalDateTime;

public record ProjectResponse (


        Long id,
        String code,
        String name,
        String description,
        ProjectStatus status,
        UserSummary owner,
        int taskCount,
        LocalDateTime createdAt
){
}

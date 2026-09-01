package tech.ekya.taskflow.report.dto;

public record UserWorkloadResponse(
        String fullName,
        Long highCount,
        Long mediumCount,
        Long lowCount


) {

}

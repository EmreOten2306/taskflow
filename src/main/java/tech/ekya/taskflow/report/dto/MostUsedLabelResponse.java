package tech.ekya.taskflow.report.dto;

public record MostUsedLabelResponse(

       String labelName,
        Long usageCount
) {
}

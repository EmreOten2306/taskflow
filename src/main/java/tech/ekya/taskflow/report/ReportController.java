package tech.ekya.taskflow.report;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ekya.taskflow.report.dto.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(
        name = "Reports",
        description = "Reporting and analytics operations"
)
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "Get project status breakdown",
            description = "Returns the status breakdown of tasks for a project"
    )
    @ApiResponse(responseCode = "200", description = "Project status breakdown retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @GetMapping("/projects/{id}/status-breakdown")
    public ProjectStatusBreakdownResponse getProjectStatusBreakdown(
            @PathVariable("id") Long projectId
    ) {
        return reportService.getProjectStatusBreakdown(projectId);
    }

    @Operation(
            summary = "Get user workload",
            description = "Returns the workload information for users"
    )
    @ApiResponse(responseCode = "200", description = "User workload retrieved successfully")
    @GetMapping("/workload")
    public List<UserWorkloadResponse> getUserWorkload() {
        return reportService.getUserWorkloads();
    }

    @Operation(
            summary = "overdue tasks and how late they are",
            description = "Returns all overdue tasks"
    )
    @ApiResponse(responseCode = "200", description = "Overdue tasks retrieved successfully")
    @GetMapping("/overdue")
    public List<TaskOverdueResponse> getOverdueTasks() {
        return reportService.getOverdueTasks();
    }

    @Operation(
            summary = "Get weekly completion trend",
            description = "Returns the weekly task completion trend"
    )
    @ApiResponse(responseCode = "200", description = "Weekly completion trend retrieved successfully")
    @GetMapping("/completion-trend")
    public List<WeeklyCompletionTrendResponse> getWeeklyCompletionTrend() {
        return reportService.getWeeklyCompletionTrendResponse();
    }

    @Operation(
            summary = "Get most used labels",
            description = "Returns the most frequently used labels"
    )
    @ApiResponse(responseCode = "200", description = "Most used labels retrieved successfully")
    @GetMapping("/labels/top")
    public List<MostUsedLabelResponse> getMostUsedLabels() {
        return reportService.getMostUsedLabels();
    }

    @Operation(
            summary = "Get project health",
            description = "Returns project health information based on task metrics"
    )
    @ApiResponse(responseCode = "200", description = "Project health information retrieved successfully")
    @GetMapping("/projects/health")
    public List<ProjectHealthResponse> getProjectHealth() {
        return reportService.getProjectHealth();
    }
}

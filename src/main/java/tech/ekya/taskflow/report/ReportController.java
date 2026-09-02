package tech.ekya.taskflow.report;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ekya.taskflow.report.dto.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/projects/{id}/status-breakdown")
    public ProjectStatusBreakdownResponse getProjectStatusBreakdown(@PathVariable ("id") Long projectId) {
        return reportService.getProjectStatusBreakdown(projectId);

    }
    @GetMapping("/workload")
    public List<UserWorkloadResponse> getUserWorkload() {
        return reportService.getUserWorkloads();
    }
    @GetMapping ("/overdue")
    public List<TaskOverdueResponse> getOverdueTasks() {
        return reportService.getOverdueTasks();
    }
    @GetMapping ("/completion-trend")
    public List<WeeklyCompletionTrendResponse> getWeeklyCompletionTrend() {
        return reportService.getWeeklyCompletionTrendResponse();
    }
    @GetMapping ("/labels/top")
    public List<MostUsedLabelResponse> getMostUsedLabels() {
        return reportService.getMostUsedLabels();
    }

}

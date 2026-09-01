package tech.ekya.taskflow.report;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.report.dto.ProjectStatusBreakdownResponse;
import tech.ekya.taskflow.report.dto.TaskOverdueResponse;
import tech.ekya.taskflow.report.dto.UserWorkloadResponse;
import tech.ekya.taskflow.task.Task;

import java.util.List;

@Transactional
@Service
public class ReportService {
    private final ReportJdbcRepository reportJdbcRepository;
    public ReportService(ReportJdbcRepository reportJdbcRepository) {
        this.reportJdbcRepository = reportJdbcRepository;
    }

    public List<TaskOverdueResponse> getOverdueTasks() {
        return reportJdbcRepository.getTaskOverdue();
    }

    public List<UserWorkloadResponse> getUserWorkloads() {
        return  reportJdbcRepository.getUserWorkload();
    }

    public ProjectStatusBreakdownResponse getProjectStatusBreakdown(Long projectId) {

        return reportJdbcRepository.getProjectStatusBreakdown(projectId);
    }


}


package tech.ekya.taskflow.report;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.report.dto.ProjectStatusBreakdownResponse;

@Transactional
@Service
public class ReportService {
    private final ReportJdbcRepository reportJdbcRepository;
    public ReportService(ReportJdbcRepository reportJdbcRepository) {
        this.reportJdbcRepository = reportJdbcRepository;
    }

    public ProjectStatusBreakdownResponse getProjectStatusBreakdown(Long projectId) {

        return reportJdbcRepository.getProjectStatusBreakdown(projectId);
    }

}


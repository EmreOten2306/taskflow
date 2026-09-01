package tech.ekya.taskflow.report;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tech.ekya.taskflow.report.dto.ProjectStatusBreakdownResponse;


@Repository
public class ReportJdbcRepository {


    private final JdbcTemplate jdbcTemplate;
    public ReportJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public ProjectStatusBreakdownResponse getProjectStatusBreakdown(Long projectId) {

        String sql = """
    SELECT
        COUNT(*) FILTER (WHERE status = 'TODO') AS todo_count,
        COUNT(*) FILTER (WHERE status = 'IN_PROGRESS') AS in_progress_count,
        COUNT(*) FILTER (WHERE status = 'IN_REVIEW') AS in_review_count,
        COUNT(*) FILTER (WHERE status = 'DONE') AS done_count
    FROM task
    WHERE project_id = ?
    """;

        RowMapper<ProjectStatusBreakdownResponse> rowMapper =
                (rs, rowNum) -> {
                    Long todoCount = rs.getLong("todo_count");
                    Long doneCount = rs.getLong("done_count");
                    Long inProgressCount = rs.getLong("in_progress_count");
                    Long inReviewCount = rs.getLong("in_review_count");

                    return new ProjectStatusBreakdownResponse(
                            todoCount,
                            inProgressCount,
                            inReviewCount,
                            doneCount
                    );
                };

        return jdbcTemplate.queryForObject(
                sql,
                rowMapper,
                projectId
        );


    }
}

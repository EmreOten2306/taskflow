package tech.ekya.taskflow.report;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tech.ekya.taskflow.report.dto.ProjectStatusBreakdownResponse;
import tech.ekya.taskflow.report.dto.TaskOverdueResponse;
import tech.ekya.taskflow.report.dto.UserWorkloadResponse;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public class ReportJdbcRepository {


    private final JdbcTemplate jdbcTemplate;
    public ReportJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TaskOverdueResponse>  getTaskOverdue() {
        String sql = """
                SELECT
                id,
                title,
                due_Date,
                CURRENT_DATE - due_date::date AS delay_days
                FROM task
                WHERE due_date <CURRENT_DATE
                AND status != 'DONE'
                """;
        RowMapper<TaskOverdueResponse> rowMapper =
                (rs, rowNum) -> {
                    Long id = rs.getLong("id");
                    String title = rs.getString("title");
                    LocalDateTime dueDate = rs.getTimestamp("due_date").toLocalDateTime();
                    Long  delay_days = rs.getLong("delay_days");

                    return new TaskOverdueResponse(
                            id,
                            title,
                            dueDate,
                            delay_days
                    );
                };

        return jdbcTemplate.query(
                sql,
                rowMapper
        );

    }

    public List<UserWorkloadResponse> getUserWorkload() {
        String sql = """
                SELECT
                app_user.full_name,
                SUM(CASE
                	WHEN priority = 'HIGH' THEN 1
                	ELSE 0
                END
                ) AS high_count,
                SUM(CASE
                	WHEN priority = 'MEDIUM' THEN 1
                	ELSE 0
                END
                ) AS medium_count,
                SUM(CASE
                	WHEN priority = 'LOW' THEN 1
                	ELSE 0
                END
                ) AS low_count
                FROM app_user
                LEFT JOIN task
                ON app_user.id = task.assignee_id
                AND status IN('TODO','IN_PROGRESS','IN_REVIEW')
                GROUP BY app_user.full_name;
                """;
        RowMapper<UserWorkloadResponse> rowMapper =
                (rs, rowNum) -> {
                    String fullName = rs.getString("full_Name");
                    Long highCount = rs.getLong("high_count");
                    Long mediumCount = rs.getLong("medium_count");
                    Long lowCount = rs.getLong("low_count");

                    return new UserWorkloadResponse(
                            fullName,
                             highCount,
                            mediumCount,
                            lowCount
                    );
                };

        return jdbcTemplate.query(
                sql,
                rowMapper
        );

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

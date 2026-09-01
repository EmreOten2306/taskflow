package tech.ekya.taskflow.report;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tech.ekya.taskflow.report.dto.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public class ReportJdbcRepository {


    private final JdbcTemplate jdbcTemplate;
    public ReportJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProjectHealthResponse> getProjectHealth() {

        String sql = """
            WITH project_stats AS (
                SELECT
                    project.id AS project_id,
                    project.name AS project_name,
                    COUNT(task.id) AS total_tasks,

                    COUNT(
                        CASE
                            WHEN task.status = 'DONE' THEN 1
                        END
                    ) AS completed_tasks,

                    SUM(
                        CASE
                            WHEN task.due_date < CURRENT_DATE
                                 AND task.status != 'DONE'
                            THEN 1
                            ELSE 0
                        END
                    ) AS overdue_tasks,

                    AVG(
                        CASE
                            WHEN task.status = 'DONE'
                            THEN EXTRACT(
                                EPOCH FROM (task.completed_at - task.created_at)
                            ) / 86400
                        END
                    ) AS average_completion_days

                FROM project
                LEFT JOIN task
                    ON project.id = task.project_id

                GROUP BY
                    project.id,
                    project.name
            )

            SELECT
                project_name,

                ROUND(
                    completed_tasks * 100.0
                    / NULLIF(total_tasks, 0),
                    2
                ) AS completion_percentage,

                ROUND(
                    overdue_tasks * 100.0
                    / NULLIF(total_tasks, 0),
                    2
                ) AS overdue_percentage,

                ROUND(
                    average_completion_days,
                    2
                ) AS average_completion_days

            FROM project_stats

            ORDER BY project_name;
            """;

        RowMapper<ProjectHealthResponse> rowMapper =
                (rs, rowNum) -> {
                    String projectName = rs.getString("project_name");
                    BigDecimal completionPercentage =
                            rs.getBigDecimal("completion_percentage");
                    BigDecimal overduePercentage =
                            rs.getBigDecimal("overdue_percentage");
                    BigDecimal averageCompletionDays =
                            rs.getBigDecimal("average_completion_days");

                    return new ProjectHealthResponse(
                            projectName,
                            completionPercentage,
                            overduePercentage,
                            averageCompletionDays
                    );
                };

        return jdbcTemplate.query(
                sql,
                rowMapper
        );
    }

    public List<MostUsedLabelResponse> getMostUsedLabels() {
        String sql = """
                SELECT
                    label.name,
                    COUNT(task_label.tasks_id) AS usage_count
                FROM label
                JOIN task_label
                    ON label.id = task_label.labels_id
                GROUP BY label.name
                ORDER BY usage_count DESC
                LIMIT 10;
                """;

        RowMapper<MostUsedLabelResponse> rowMapper =
                (rs, rowNum) -> {
            String labelName = rs.getString("name");
            Long usageCount = rs.getLong("usage_count");
            return new MostUsedLabelResponse(
                    labelName,
                    usageCount);
                };

        return jdbcTemplate.query(
                sql,
                rowMapper
        );

    }

    public List<WeeklyCompletionTrendResponse> getWeeklyCompletionTrend() {
        String sql = """
                SELECT
                    weeks.week_start,
                    COUNT(task.id) AS completed_count
                FROM generate_series(
                    date_trunc('week', CURRENT_DATE) - interval '7 weeks',
                    date_trunc('week', CURRENT_DATE),
                    interval '1 week'
                ) AS weeks(week_start)
                LEFT JOIN task
                    ON weeks.week_start = date_trunc('week', task.completed_at)
                    AND task.status = 'DONE'
                GROUP BY weeks.week_start
                ORDER BY weeks.week_start;
                
                """;
        RowMapper<WeeklyCompletionTrendResponse> rowMapper =
                (rs, rowNum) -> {
                    LocalDate date = rs.getDate("week_start").toLocalDate();
                    Long completedCount = rs.getLong("completed_count");

                    return new WeeklyCompletionTrendResponse(
                            date,
                            completedCount
                    );
                };

        return jdbcTemplate.query(
                sql,
                rowMapper
        );

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

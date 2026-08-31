package tech.ekya.taskflow.report;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.ekya.taskflow.task.TaskStatusCountProjection;

import java.util.List;

@Repository
public class ReportJdbcRepository {


    private final JdbcTemplate jdbcTemplate;
    public ReportJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



}

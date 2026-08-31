package tech.ekya.taskflow.task;

public interface TaskStatusCountProjection {

    String getStatus();

    Long getTaskCount();
}
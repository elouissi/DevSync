package org.project.error;

public class TaskAlreadyExistsException extends RuntimeException {
    public TaskAlreadyExistsException(int taskId) {
        super("Task with ID " + taskId + " already exists");
    }
}
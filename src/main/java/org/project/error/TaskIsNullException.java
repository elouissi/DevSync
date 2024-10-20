package org.project.error;

public class TaskIsNullException extends RuntimeException{
    public TaskIsNullException(){
        super("Task must be not null");
    }
}

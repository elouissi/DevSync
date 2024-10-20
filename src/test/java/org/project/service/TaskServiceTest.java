package org.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.Enum.TypeStatus;
import org.project.entite.Tag;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.error.TaskAlreadyExistsException;
import org.project.error.TaskIsNullException;
import org.project.repositorie.TaskRepository;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository ;
    @InjectMocks
    private TaskService taskService ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void TastSaveTaskNull(){
        assertThrows(TaskIsNullException.class,()-> taskService.createTask(null));
        verify(taskRepository,never()).createTask(any(Task.class));
    }
    @Test
    public void testCreateTaskAlreadyExists() {
        // Arrange
        int existingTaskId = 1;
        Task existingTask = createSampleTask(existingTaskId);

        when(taskRepository.getTaskById(existingTaskId)).thenReturn(existingTask);

        Task newTask = createSampleTask(existingTaskId);

        assertThrows(TaskAlreadyExistsException.class, () -> taskService.createTask(newTask));

        verify(taskRepository).getTaskById(existingTaskId);
        verify(taskRepository, never()).createTask(any(Task.class));
    }

    @Test
    public void testDeleteTask() {
        taskService.deleteTask(1);
        verify(taskRepository).deleteTask(1);
    }
    @Test
    public void testDeleteTaskNotFound() {
        int nonExistingTaskId = 999;

        doThrow(new TaskIsNullException()).when(taskRepository).deleteTask(nonExistingTaskId);

        assertThrows(TaskIsNullException.class, () -> taskService.deleteTask(nonExistingTaskId));
        verify(taskRepository).deleteTask(nonExistingTaskId);
    }

    @Test
    public void testUpdateTaskNotFound() {
        Task taskToUpdate = createSampleTask(1);

        when(taskRepository.getTaskById(1)).thenReturn(null);

        assertThrows(TaskIsNullException.class, () -> taskService.updateTask(taskToUpdate));

        verify(taskRepository, never()).updateTask(taskToUpdate);
    }
    @Test
    public void testCreateTaskWithInvalidDate() {
        Task taskWithInvalidDate = createSampleTask(1);
        taskWithInvalidDate.setDateDebut(LocalDate.now().plusDays(10));
        taskWithInvalidDate.setDateFin(LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(taskWithInvalidDate));


        verify(taskRepository, never()).createTask(taskWithInvalidDate);
    }

    private Task createSampleTask(int id) {
        Task task = new Task();
        task.setId(id);
        task.setTitre("Sample Task");
        task.setDescription("This is a sample task");
        task.setStatus(TypeStatus.en_attend);
        task.setCreatedBy(new User());
        task.setDateDebut(LocalDate.now());
        task.setDateFin(LocalDate.now().plusDays(7));
        task.setRequested(false);
        task.setTags(Arrays.asList(new Tag()));
        return task;
    }

    @AfterEach
    void tearDown() {
    }
}
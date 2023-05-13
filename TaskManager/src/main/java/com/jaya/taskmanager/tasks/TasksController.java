package com.jaya.taskmanager.tasks;

import com.jaya.taskmanager.service.TasksService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    List<Task> taskList = new ArrayList<Task>();
    private int nextTaskId = 1;

    private final TasksService tasksService;

    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }


// List all tasks endpoint
    @GetMapping("")
    public List<Task> getAllTasks(@RequestParam(required = false) Boolean status,
                                  @RequestParam(required = false, defaultValue = "dateAsc") String sort) {

        return tasksService.getAllTasks(status,sort);
    }

    @PostMapping("")
    Task createTask(@RequestBody Task task) {
        var task1 =tasksService.createTask(task);
        return task1;
    }

    @GetMapping("/{id}")
    Task getTaskById(@PathVariable("id") Integer id) {
        var foundTask = taskList.stream().filter(
                        task -> task.getId().equals(id)
                ).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return foundTask;
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") Integer id, @RequestBody Task updatedTask) {
        var taskToUpdate = taskList.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskToUpdate.setCompleted(updatedTask.getCompleted());
        taskToUpdate.setDueDate(updatedTask.getDueDate());

        return taskToUpdate;
    }

    @DeleteMapping("")
    public String deleteTask(@RequestParam(required = false) Integer taskId,
                             @RequestParam(required = false) Boolean status) {
        int initialSize = taskList.size();

        // Filter tasks by status
        if (status != null) {
            taskList.removeIf(Task::getCompleted);
        }

        if (taskId != null) {
            var taskToUpdate = taskList.stream()
                    .filter(task -> task.getId().equals(taskId))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

            taskList.removeIf(task -> task.getId().equals(taskId));
        }


        int finalSize = taskList.size();
        int removedCount = initialSize - finalSize;
        if (removedCount == 0) {
            return "No completed tasks found.";
        } else if (removedCount == 1) {
            return "1 completed task removed successfully.";
        } else {
            return removedCount + " completed tasks removed successfully.";
        }

    }

}

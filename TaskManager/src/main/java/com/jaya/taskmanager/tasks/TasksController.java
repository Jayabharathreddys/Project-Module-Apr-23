package com.jaya.taskmanager.tasks;

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

    /*
        @GetMapping("")
        List<Task> getAllTasks(){
            return taskList;
        }
    */
// List all tasks endpoint
    @GetMapping("")
    public List<Task> getAllTasks(@RequestParam(required = false) Boolean status,
                                  @RequestParam(required = false, defaultValue = "dateAsc") String sort) {
        Stream<Task> taskStream = taskList.stream();

        // Filter tasks by status
        if (status != null) {
            taskStream = taskStream.filter(task -> task.getCompleted().equals(status));
        }

        // Sort tasks by due date
        Comparator<Task> comparator = Comparator.comparing(Task::getDueDate);
        if (sort.equalsIgnoreCase("dateDesc")) {
            comparator = comparator.reversed();
        }
        taskStream = taskStream.sorted(comparator);

        // Return the sorted and filtered list of tasks
        return taskStream.collect(Collectors.toList());
    }

    @PostMapping("")
    Task createTask(@RequestBody Task task) {
        // Throw an error if the task name is missing
        if (task.getName() == null || task.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task name is missing");
        }

        // Throw an error if the task due date is missing or invalid (before today)
        if (task.getDueDate() == null || task.getDueDate().before(new Date())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task due date is missing or invalid");
        }

        task.setId(nextTaskId++);
        taskList.add(task);
        return task;
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

package com.example.newsample.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    List<Task> taskList =new ArrayList<>();
    private int nextTaskId =1;
    @GetMapping("")
    List<Task> getAllTasks(){
      return taskList;
    }

    @PostMapping("")
    Task createTask(@RequestBody Task task){
        task.setId(nextTaskId++);
        taskList.add(task);
        return task;
    }

    @GetMapping("/{id}")
    Task getTaskById(@PathVariable("id") Integer id){
        var foundTask = taskList.stream().filter(
                task -> task.getId().equals(id)
        ).findFirst().orElse(null);
    return foundTask;
    }

    @PatchMapping("/{id}")
    Task updateTask(@PathVariable("id") Integer id, @RequestBody Task updatedTask) {

        var taskToUpdate = taskList.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskToUpdate.setCompleted(updatedTask.getCompleted());
        taskToUpdate.setDueDate(updatedTask.getDueDate());

        return taskToUpdate;
   }


    @DeleteMapping("/{taskId}")
    String deleteTask(@PathVariable("taskId") Integer taskId) {
        int initialSize = taskList.size();
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

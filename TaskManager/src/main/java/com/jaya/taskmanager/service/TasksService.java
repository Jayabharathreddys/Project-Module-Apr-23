package com.jaya.taskmanager.service;

import com.jaya.taskmanager.tasks.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TasksService {
    private List<Task> tasks = new ArrayList<>();
    private Integer id = 0;

    public List<Task> getAllTasks(Boolean status, String sort) {

        Stream<Task> taskStream = tasks.stream();

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

    public Task getTaskById(Integer id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        throw new TaskNotFoundException(id);
    }

    // TODO 04: generate error for invalid dueDate (before today)
    // TODO 05: generate error for invalid name (less than 5 char, or more than 100 char)
    public Task createTask(Task task) {


        // Throw an error if the task name is missing
        if (task.getName() == null || task.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task name is missing");
        }

        // Throw an error if the task due date is missing or invalid (before today)
        if (task.getDueDate() == null || task.getDueDate().before(new Date())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task due date is missing or invalid");
        }

        Task task1 = new Task(id++, task.getName(), task.getDueDate(), false);
        tasks.add(task1);

        return task;
    }

    // TODO 06: generate error for invalid dueDate (before today)
    public Task updateTask(Integer id, Date dueDate, Boolean completed) {
        Task task = getTaskById(id);
        if (dueDate != null) {
            task.setDueDate(dueDate);
        }
        if (completed != null) {
            task.setCompleted(completed);
        }
        return task;
    }

    public void deleteTask(Integer id) {
        Task task = getTaskById(id);
        tasks.remove(task);
    }

    class TaskNotFoundException extends IllegalStateException {
        public TaskNotFoundException(Integer id) {
            super("Task with id " + id + " not found");
        }
    }

    public static class TaskFilter {
        Date beforeDate;
        Date afterDate;
        Boolean completed;

        static TaskFilter fromQueryParams(Date beforeDate, Date afterDate, Boolean completed) {
            if (beforeDate == null && afterDate == null && completed == null) {
                return null;
            }
            TaskFilter taskFilter = new TaskFilter();
            taskFilter.beforeDate = beforeDate;
            taskFilter.afterDate = afterDate;
            taskFilter.completed = completed;
            return taskFilter;
        }
    }
}

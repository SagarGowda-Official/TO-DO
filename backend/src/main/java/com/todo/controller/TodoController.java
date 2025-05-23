package com.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.model.Todo;
import com.todo.repository.TodoRepository;
import com.todo.service.TodoService;
import com.todo.util.SummaryHelper;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TodoController {
    @Autowired
    private TodoService service;

    @Autowired
    private SummaryHelper summaryHelper;

    @GetMapping("/todos")
    public List<Todo> getTodos() {
        return service.getAll();
    }

   @PostMapping("/todos")
public Todo addTodo(@RequestBody Todo todo) {
    System.out.println(">>> Received Todo: " + todo.getTask());
    return service.addTodo(todo);
}

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        service.deleteTodo(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/summarize")
    public ResponseEntity<?> summarizeAndSend() {
        try {
            String summary = summaryHelper.summarizeTodos(service.getAll());
            summaryHelper.sendToSlack(summary);
            return ResponseEntity.ok("Summary sent to Slack");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed: " + e.getMessage());
        }
    }

@PutMapping("/todos/{id}")
public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
    Optional<Todo> optionalTodo = service.getById(id);
    if (optionalTodo.isPresent()) {
        Todo todo = optionalTodo.get();
        todo.setTask(updatedTodo.getTask());
        Todo saved = service.addTodo(todo); // reuse the save method
        return ResponseEntity.ok(saved);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found with ID: " + id);
    }
}

}




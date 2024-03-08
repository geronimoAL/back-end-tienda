package com.backend.spring3.tienda.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.spring3.tienda.dto.TodoDto;
import com.backend.spring3.tienda.service.TodoService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;

    // Build Add Todo REST API

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public ResponseEntity<TodoDto> addTodo(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) throws IOException {
        TodoDto todoDto = new TodoDto();
        todoDto.setTitle(title);
        todoDto.setDescription(description);
        todoDto.setCompleted(true);
        TodoDto savedTodo = todoService.addTodo(todoDto, file);

        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    // Build Get Todo REST API
    // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId) {
        TodoDto todoDto = todoService.getTodo(todoId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    // Build Get All Todos REST API
    // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        // return new ResponseEntity<>(todos, HttpStatus.OK);
        return ResponseEntity.ok(todos);
    }

    // Build Update Todo REST API
    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @PathVariable("id") Long todoId) throws IOException {

        TodoDto todoDto = new TodoDto();
        todoDto.setTitle(title);
        todoDto.setDescription(description);
        todoDto.setCompleted(true);
        TodoDto updatedTodo = todoService.updateTodo(todoDto, todoId,file);
        return ResponseEntity.ok(updatedTodo);
    }

    // Build Delete Todo REST API
    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId)throws IOException {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo deleted successfully!.");
    }

    // // Build Complete Todo REST API
    // // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    // @PatchMapping("{id}/complete")
    // public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId) {
    //     TodoDto updatedTodo = todoService.completeTodo(todoId);
    //     return ResponseEntity.ok(updatedTodo);
    // }

    // // Build In Complete Todo REST API
    // // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    // @PatchMapping("{id}/in-complete")
    // public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long todoId) {
    //     TodoDto updatedTodo = todoService.inCompleteTodo(todoId);
    //     return ResponseEntity.ok(updatedTodo);
    // }

}

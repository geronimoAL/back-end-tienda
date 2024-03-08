package com.backend.spring3.tienda.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.spring3.tienda.dto.TodoDto;

public interface TodoService {
    
    
    TodoDto addTodo(TodoDto todoDto,MultipartFile file) throws IOException ;

    TodoDto getTodo(Long id);

    List<TodoDto> getAllTodos();

    TodoDto updateTodo(TodoDto todoDto, Long id,MultipartFile file)throws IOException;

    void deleteTodo(Long id)throws IOException;

    // TodoDto completeTodo(Long id);

    // TodoDto inCompleteTodo(Long id);
}

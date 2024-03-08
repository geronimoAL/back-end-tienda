package com.backend.spring3.tienda.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.spring3.tienda.dto.TodoDto;
import com.backend.spring3.tienda.entity.Todo;
import com.backend.spring3.tienda.exception.ResourceNotFoundException;
import com.backend.spring3.tienda.repository.TodoRepository;
import com.backend.spring3.tienda.service.CloudinaryService;
import com.backend.spring3.tienda.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// import java.awt.image.BufferedImage;

@Slf4j
@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

      private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    private TodoRepository todoRepository;

    private ModelMapper modelMapper;

    private CloudinaryService cloudinaryService;

    @Override
    public TodoDto addTodo(TodoDto todoDto,MultipartFile file) throws IOException   {
        // BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        
    
        Map result = cloudinaryService.upload(file);
        Todo todo = modelMapper.map(todoDto, Todo.class);
        todo.setCompleted(false);
        todo.setImagen((String) result.get("url"));
        todo.setCloudinaryId((String) result.get("public_id"));
        Todo savedTodo = todoRepository.save(todo);
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));

        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id,MultipartFile file) throws IOException {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el id : " + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(true);
        Map delete = cloudinaryService.delete(todo.getCloudinaryId());
        logger.info("El resultado de borrar la imagen me da : "+ delete);
        Map upload = cloudinaryService.upload(file);
        logger.info("El resultado de subir la imagen me da : "+ upload);
        todo.setImagen((String) upload.get("url"));
        todo.setCloudinaryId((String) upload.get("public_id"));
        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) throws IOException {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));
        Map delete = cloudinaryService.delete(todo.getCloudinaryId());
        logger.info("El resultado de borrar el libro me da : "+ delete);
        todoRepository.deleteById(id);
    }

    // @Override
    // public TodoDto completeTodo(Long id) {
    //     Todo todo = todoRepository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

    //     todo.setCompleted(Boolean.TRUE);

    //     Todo updatedTodo = todoRepository.save(todo);

    //     return modelMapper.map(updatedTodo, TodoDto.class);
    // }

    // @Override
    // public TodoDto inCompleteTodo(Long id) {
    //     Todo todo = todoRepository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

    //     todo.setCompleted(Boolean.FALSE);

    //     Todo updatedTodo = todoRepository.save(todo);

    //     return modelMapper.map(updatedTodo, TodoDto.class);
    // }

}

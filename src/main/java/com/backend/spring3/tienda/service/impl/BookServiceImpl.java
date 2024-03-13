package com.backend.spring3.tienda.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

// import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import com.backend.spring3.tienda.dto.CategoryDto;
import com.backend.spring3.tienda.dto.BookDto;
import com.backend.spring3.tienda.entity.Author;
import com.backend.spring3.tienda.entity.Category;
import com.backend.spring3.tienda.entity.Book;
import com.backend.spring3.tienda.exception.ResourceNotFoundException;
import com.backend.spring3.tienda.repository.AuthorRepository;
import com.backend.spring3.tienda.repository.CategoryRepository;
import com.backend.spring3.tienda.repository.BookRepository;
import com.backend.spring3.tienda.service.CloudinaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.backend.spring3.tienda.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

        private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

        private BookRepository bookRepository;

        private AuthorRepository authorRepository;

        private CategoryRepository categoryRepository;

        private ObjectMapper objectMapper;

        private ModelMapper modelMapper;

        private CloudinaryService cloudinaryService;

        @Transactional
        @Override
        public BookDto addBook(String title, String editorial, String description,
                        String date, String amount, String price, String authorId, String categorias,
                        MultipartFile file)
                        throws IOException {

                logger.info("Entrando en bookServiceImpl");

                // Pasar el json de categorias a un set de la entidad categoria
                CategoryDto[] categoriasArray = objectMapper.readValue(categorias, CategoryDto[].class);

                // Tambien podria obtener un array te category asi con el id y el nombre
                // Set<Category> categoriasNombres = Arrays.stream(categoriasArray)
                // .map(category -> new Category(category.getId().toString(),
                // category.getName().toString()))
                // .collect(Collectors.toSet());

                Set<Category> categoriesNombres = Arrays.stream(categoriasArray)
                                .map(category -> categoryRepository.findById(category.getId())
                                                .orElseThrow(() -> new ResourceNotFoundException(
                                                                "Category not found with id:" + category.getId())))
                                .collect(Collectors.toSet());

                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(date, formato);

                Map result = cloudinaryService.upload(file);

                Book libro = new Book();
                libro.setTitle(title);
                libro.setEditorial(editorial);
                libro.setDescription(description);
                libro.setPrice(price);
                libro.setAmount(Integer.parseInt(amount));
                libro.setImageUrl((String) result.get("url"));
                libro.setCloudinaryId((String) result.get("public_id"));
                Author author = authorRepository.findById(authorId)
                                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id:"));
                ;
                libro.setAuthor(author);
                libro.setCategories(categoriesNombres);
                libro.setPublicationDate(fecha);
                Book savedTodo = bookRepository.save(libro);

                BookDto savedTodoDto = modelMapper.map(savedTodo, BookDto.class);

                return savedTodoDto;
        }

        @Override
        public BookDto getTodo(String id) {
                Book libro = bookRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));

                return modelMapper.map(libro, BookDto.class);
        }

        @Override
        public List<BookDto> getAllTodos() {
                List<Book> todos = bookRepository.findAll();

                return todos.stream().map((libro) -> modelMapper.map(libro, BookDto.class))
                                .collect(Collectors.toList());
        }

        @Override
        public BookDto updateTodo(BookDto libroDto, Long id, MultipartFile file) throws IOException {

                Book todo = bookRepository.findById(String.valueOf(id))
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "No se encontró el libro con id : " + id));
                todo.setTitle(libroDto.getTitle());
                todo.setDescription(libroDto.getDescription());
                Map delete = cloudinaryService.delete(todo.getCloudinaryId());
                logger.info("El resultado de borrar la imagen me da : " + delete);
                Map upload = cloudinaryService.upload(file);
                logger.info("El resultado de subir la imagen me da : " + upload);
                todo.setImageUrl((String) upload.get("url"));
                todo.setCloudinaryId((String) upload.get("public_id"));
                Book updatedTodo = bookRepository.save(todo);

                return modelMapper.map(updatedTodo, BookDto.class);
        }

        @Override
        public void deleteTodo(Long id) throws IOException {
                Book todo = bookRepository.findById(String.valueOf(id))
                                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));
                Map delete = cloudinaryService.delete(todo.getCloudinaryId());
                logger.info("El resultado de borrar el libro me da : " + delete);
                bookRepository.deleteById(String.valueOf(id));
        }

        @Override
        public List<BookDto> searchBookXCategoryId(String idCategory) {
                List<Book> books = bookRepository.searchBookXCategoryID(idCategory);
                return books.stream().map((book) -> modelMapper.map(book, BookDto.class))
                                .collect(Collectors.toList());
        }

        @Override
        public List<BookDto> searchBookXNameOrEditorial(String name) {
                List<Book> books = bookRepository.searchBookXNameOrEditorial(name);
                return books.stream().map((book) -> modelMapper.map(book, BookDto.class))
                                .collect(Collectors.toList());
        }
        

}

package com.backend.spring3.tienda.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// import javax.imageio.ImageIO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.backend.spring3.tienda.dto.CategoryDto;
import com.backend.spring3.tienda.dto.BookDto;
import com.backend.spring3.tienda.entity.Author;
import com.backend.spring3.tienda.entity.Category;
import com.backend.spring3.tienda.entity.User;
import com.backend.spring3.tienda.entity.Book;
import com.backend.spring3.tienda.exception.ResourceNotFoundException;
import com.backend.spring3.tienda.repository.AuthorRepository;
import com.backend.spring3.tienda.repository.CategoryRepository;
import com.backend.spring3.tienda.repository.UserRepository;
import com.backend.spring3.tienda.repository.BookRepository;
import com.backend.spring3.tienda.service.CloudinaryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

        private UserRepository userRepository;

        @Transactional
        @Override
        public BookDto addBook(String idUser,String title, String editorial, String description,String date, String amount, String price, String authorId, String categories,MultipartFile file)throws IOException {

                logger.info("Entrando en bookServiceImpl");

                User user=userRepository.findByEmail(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con "));

                Set<Category> categoriesNombres = establishCategories(categories);

                LocalDate fecha = transformToDate(date);

                Map result = cloudinaryService.upload(file);

                Author author = authorRepository.findById(authorId)
                                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id:"));

                Book book = Book.builder()
                .title(title)
                .editorial(editorial)
                .description(description)
                .price(price)
                .amount(1)
                .inStock(Integer.parseInt(amount))
                .imageUrl((String) result.get("url"))
                .cloudinaryId((String) result.get("public_id"))
                .author(author)
                .categories(categoriesNombres)
                .publicationDate(fecha)
                .user(user)
                .build();
               
                Book savedTodo = bookRepository.save(book);

                BookDto savedTodoDto = modelMapper.map(savedTodo, BookDto.class);

                return savedTodoDto;
        }

        @Override
        public BookDto getBook(String id) {
                Book libro = bookRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id :" + id));

                return modelMapper.map(libro, BookDto.class);
        }

        @Override
        public List<BookDto> getAllBooks() {
                List<Book> books = bookRepository.findAll();
                logger.info("Get all me da "+books);
                return books.stream().map(libro -> {
                        BookDto bookDto = modelMapper.map(libro, BookDto.class);
                        bookDto.setUserId(String.valueOf(libro.getUser().getId()));
                
                        bookDto.setAuthorName(libro.getAuthor().getName());

                
                        return bookDto;
        })
                .collect(Collectors.toList());
        }

        @Override
        public BookDto updateBook(String id, MultipartFile file, String title, String editorial, String description,
                        String date, String amount, String price, String authorId, String categories)
                        throws IOException {

                logger.info("Entrando en bookUpdateImpl");

                Book book = bookRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "No se encontró el libro con id : " + id));

                logger.info("con el book "+title);

                book.setTitle(  title.equals("") ?  book.getTitle():title);

                book.setDescription( description.equals("") ? book.getDescription():description);

                book.setEditorial( editorial.equals("") ? book.getEditorial(): editorial);

                book.setInStock(amount.equals("") ? book.getInStock() : Integer.parseInt(amount));

                book.setPrice(price.equals("") ? book.getPrice() : price);


                if (!date.equals("")) {
                        logger.info("Entrando a date");
                        LocalDate fecha = transformToDate(date);
                        book.setPublicationDate(fecha);
                }

                if(!authorId.equals("")){
                Author author = authorRepository.findById(authorId)
                                .orElseThrow(() -> new ResourceNotFoundException("Author no encontrado con id:"));
                book.setAuthor(author);

                }
                
                logger.info("en casi file");
                if (file != null) {
                        updateImageBook(book, file);
                }
                logger.info("en categories da "+categories);
                if(!categories.equals("[]")){
                Set<Category> categoriesNombres = establishCategories(categories);
                book.setCategories(categoriesNombres);
                }

                Book updatedTodo = bookRepository.save(book);

                return modelMapper.map(updatedTodo, BookDto.class);
        }

        @Override
        public void deleteBook(String id) throws IOException {
                Book bookSearch = bookRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con  : " + id));
                logger.info("Entrando a delete book service impl ");
                Map delete = cloudinaryService.delete(bookSearch.getCloudinaryId());
                logger.info("El resultado de borrar el libro me da : " + delete);
                bookRepository.deleteById(id);
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

        @Override
        public List<BookDto> getBookLimit() {
                List<Book> books = bookRepository.findAll();
                logger.info("Get all me da "+books);
                return books.stream()
                .limit(3)
                .map(libro -> {
                        BookDto bookDto = modelMapper.map(libro, BookDto.class);
                        bookDto.setUserId(String.valueOf(libro.getUser().getId()));
                
                        bookDto.setAuthorName(libro.getAuthor().getName());

                
                        return bookDto;
        })
                .collect(Collectors.toList());


        }

        private void updateImageBook(Book book, MultipartFile file) throws IOException {
                Map imageDelete = cloudinaryService.delete(book.getCloudinaryId());
                logger.info("El resultado de borrar la imagen me da : " + imageDelete);
                Map imageUpload = cloudinaryService.upload(file);
                logger.info("El resultado de subir la imagen me da : " + imageUpload);
                book.setImageUrl((String) imageUpload.get("url"));
                book.setCloudinaryId((String) imageUpload.get("public_id"));
        }

        private Set<Category> establishCategories(String categorias)
                        throws JsonMappingException, JsonProcessingException {

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

                return categoriesNombres;

        }

        private LocalDate transformToDate(String date) {
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(date, formato);
                return fecha;
        }

        @Override
        public List<BookDto> getBookIdUser(String idUser) {

                List<Book> books=bookRepository.booksXIdUser(idUser);
                logger.info("El resultado de los books por id  me da : " + books);
                return books.stream().map(libro -> {
                        BookDto bookDto = modelMapper.map(libro, BookDto.class);
                        bookDto.setUserId(String.valueOf(libro.getUser().getId()));
                
                        bookDto.setAuthorName(libro.getAuthor().getName());

                
                        return bookDto;
        })
                .collect(Collectors.toList());
        }

}

package hu.progmasters.firstspringdemo.service;

import hu.progmasters.firstspringdemo.domain.Book;
import hu.progmasters.firstspringdemo.domain.BookType;
import hu.progmasters.firstspringdemo.domain.Library;
import hu.progmasters.firstspringdemo.dto.incoming.AddBookToLibraryCommand;
import hu.progmasters.firstspringdemo.dto.incoming.BookCommand;
import hu.progmasters.firstspringdemo.dto.outgoing.BookDetails;
import hu.progmasters.firstspringdemo.dto.outgoing.BookDetailsResponse;
import hu.progmasters.firstspringdemo.repository.BookRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;
    private final LibraryService libraryService;
    private final ModelMapper modelMapper;


    @Autowired
    public BookService(BookRepository bookRepository, LibraryService libraryService, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.libraryService = libraryService;
        this.modelMapper = modelMapper;
    }

    public void createBook(@Valid BookCommand bookCommand) {
        Book book = new Book();
        book.setTitle(bookCommand.getTitle());
        book.setAuthor(bookCommand.getAuthor());
        book.setBookType(BookType.valueOf(bookCommand.getBookType().toUpperCase()));

        bookRepository.save(book);
    }


    public void addBookToLibrary(AddBookToLibraryCommand command) {
        Library library = libraryService.findLibraryByName(command.getLibraryName());
        Book book = bookRepository.findBookByTitle(command.getTitle())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        boolean alreadyExists = library.getBooks().stream()
                .anyMatch(b -> b.getId().equals(book.getId()));

        if (alreadyExists) {
            throw new RuntimeException("Book already exists in this library");
        }
        library.getBooks().add(book);
        book.getLibraries().add(library);
    }


    public void createBooks(@Valid List<BookCommand> bookCommands) {
        List<Book> books = bookCommands.stream()
                .map(bookCommand -> {
                    Book book = new Book();
                    book.setTitle(bookCommand.getTitle());
                    book.setAuthor(bookCommand.getAuthor());
                    book.setBookType(BookType.valueOf(bookCommand.getBookType().toUpperCase()));

                    return book;
                })
                .toList();
        bookRepository.saveAll(books);
    }


//    public BookDetails getBookByTitle(String title) {
//        Book book = findBookByTitle(title);
//        BookDetails bookDetails = modelMapper.map(book, BookDetails.class);
//        return bookDetails;
//    }

    public List<BookDetailsResponse> getBooksByTitle(String title) {

        List<Book> books = bookRepository.findAllByTitle(title);

        return books.stream()
                .map(book -> {
                    BookDetailsResponse response = new BookDetailsResponse();
                    response.setId(book.getId());
                    response.setTitle(book.getTitle());
                    response.setAuthor(book.getAuthor());
                    response.setBookType(book.getBookType().name());

                    List<String> libraryNames = book.getLibraries().stream()
                            .map(Library::getName)
                            .toList();

                    response.setLibraryNames(libraryNames);
                    return response;
                })
                .toList();
    }



    public Book findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title)
                .orElseThrow();
    }

}

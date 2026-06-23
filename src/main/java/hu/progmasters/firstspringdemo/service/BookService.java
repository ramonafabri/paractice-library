package hu.progmasters.firstspringdemo.service;

import hu.progmasters.firstspringdemo.domain.Book;
import hu.progmasters.firstspringdemo.domain.BookType;
import hu.progmasters.firstspringdemo.domain.Library;
import hu.progmasters.firstspringdemo.dto.incoming.BookCommand;
import hu.progmasters.firstspringdemo.dto.outgoing.BookDetails;
import hu.progmasters.firstspringdemo.dto.outgoing.BookDetailsResponse;
import hu.progmasters.firstspringdemo.repository.BookRepository;
import hu.progmasters.firstspringdemo.repository.LibraryRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final LibraryRepository libraryRepository;



    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.libraryRepository = libraryRepository;
    }

    public void createBook(@Valid BookCommand bookCommand) {
        Book book = new Book();
        book.setTitle(bookCommand.getTitle());
        book.setAuthor(bookCommand.getAuthor());
        book.setBookType(BookType.valueOf(bookCommand.getBookType().toUpperCase()));

        bookRepository.save(book);
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


    public BookDetails getBookByTitle(String title) {
        Book book = findBookByTitle(title);
        BookDetails bookDetails = modelMapper.map(book, BookDetails.class);
        return bookDetails;
    }


    public void addBookToLibrary(String title, String libraryName) {

        Library library = libraryRepository.findLibraryByName(libraryName)
                .orElseThrow(() -> new IllegalArgumentException("Library not found: " + libraryName));

        Book book = bookRepository.findBookByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + title));

        boolean exists = library.getBooks().stream()
                .anyMatch(b -> b.getId().equals(book.getId()));

        if (exists) {
            throw new IllegalStateException("Book already exists in this library");
        }

        library.getBooks().add(book);
        book.getLibraries().add(library);

        libraryRepository.save(library);
    }


    public List<BookDetailsResponse> getBooksByTitle(String title) {

        List<Book> books = bookRepository.findAllByTitleIgnoreCase(title);

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

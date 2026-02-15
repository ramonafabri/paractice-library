package hu.progmasters.firstspringdemo.service;

import hu.progmasters.firstspringdemo.domain.Book;
import hu.progmasters.firstspringdemo.domain.BookType;
import hu.progmasters.firstspringdemo.domain.Library;
import hu.progmasters.firstspringdemo.dto.incoming.BookCommand;
import hu.progmasters.firstspringdemo.dto.outgoing.BookDetails;
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
    private  final ModelMapper modelMapper;


    @Autowired
    public BookService(BookRepository bookRepository, LibraryService libraryService,  ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.libraryService = libraryService;
        this.modelMapper = modelMapper;
    }

   public void createBook(@Valid BookCommand bookCommand) {
       Library library = libraryService.findLibraryByName(bookCommand.getLibraryName());
       Book book = new Book();
       book.setTitle(bookCommand.getTitle());
       book.setAuthor(bookCommand.getAuthor());
       book.setBookType(BookType.valueOf(bookCommand.getBookType().toUpperCase()));
       book.setLibrary(library);
       bookRepository.save(book);
   }

   public void createBooks(@Valid List<BookCommand> bookCommands) {
        List<Book> books = bookCommands.stream()
                .map(bookCommand -> {
                    Book book = new Book();
                    book.setTitle(bookCommand.getTitle());
                    book.setAuthor(bookCommand.getAuthor());
                    book.setBookType(BookType.valueOf(bookCommand.getBookType().toUpperCase()));
                    book.setLibrary(libraryService.findLibraryByName(bookCommand.getLibraryName()));
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

    public  Book findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title)
                .orElseThrow();
    }

}

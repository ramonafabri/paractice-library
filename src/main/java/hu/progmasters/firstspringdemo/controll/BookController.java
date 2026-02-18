package hu.progmasters.firstspringdemo.controll;


import hu.progmasters.firstspringdemo.dto.incoming.AddBookToLibraryCommand;
import hu.progmasters.firstspringdemo.dto.incoming.BookCommand;
import hu.progmasters.firstspringdemo.dto.outgoing.BookDetailsResponse;
import hu.progmasters.firstspringdemo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {


    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@Valid @RequestBody BookCommand bookCommand){
        bookService.createBook(bookCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add-to-library")
    public ResponseEntity<Void> addBookToLibrary(@RequestBody @Valid AddBookToLibraryCommand command) {
        bookService.addBookToLibrary(command);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/{title}")
//    public ResponseEntity<BookDetails> getBookByTitle(@PathVariable String title){
//        BookDetails response = bookService.getBookByTitle(title);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
    @GetMapping
    public ResponseEntity<List<BookDetailsResponse>> getBooksByTitle(@RequestParam String title) {
        List<BookDetailsResponse> response = bookService.getBooksByTitle(title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/batch")
    public void createBooks (@RequestBody List<BookCommand> bookCommandList){
        bookService.createBooks(bookCommandList);
    }

}

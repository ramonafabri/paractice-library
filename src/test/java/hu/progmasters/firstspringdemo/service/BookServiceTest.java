package hu.progmasters.firstspringdemo.service;

import hu.progmasters.firstspringdemo.configuration.ModelMapperConfig;
import hu.progmasters.firstspringdemo.domain.Book;
import hu.progmasters.firstspringdemo.domain.BookType;
import hu.progmasters.firstspringdemo.domain.Library;
import hu.progmasters.firstspringdemo.dto.incoming.BookCommand;
import hu.progmasters.firstspringdemo.dto.outgoing.BookDetailsResponse;
import hu.progmasters.firstspringdemo.repository.BookRepository;
import hu.progmasters.firstspringdemo.repository.LibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LibraryRepository libraryRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(
                bookRepository,
                new ModelMapperConfig().getModelMapper(),
                libraryRepository
        );
    }

    @Test
    void createBookShouldSaveBookWithCommandData() {
        BookCommand command = new BookCommand("Robert C. Martin", "Clean Code", "science", null);

        bookService.createBook(command);

        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookCaptor.capture());

        Book savedBook = bookCaptor.getValue();
        assertEquals("Clean Code", savedBook.getTitle());
        assertEquals("Robert C. Martin", savedBook.getAuthor());
        assertEquals(BookType.SCIENCE, savedBook.getBookType());
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void createBooksShouldSaveAllBooks() {
        List<BookCommand> commands = List.of(
                new BookCommand("Joshua Bloch", "Effective Java", "SCIENCE", null),
                new BookCommand("George Orwell", "1984", "FICTION", null)
        );

        bookService.createBooks(commands);

        ArgumentCaptor<List<Book>> booksCaptor = ArgumentCaptor.forClass(List.class);
        verify(bookRepository).saveAll(booksCaptor.capture());

        List<Book> savedBooks = booksCaptor.getValue();
        assertEquals(2, savedBooks.size());
        assertEquals("Effective Java", savedBooks.get(0).getTitle());
        assertEquals(BookType.SCIENCE, savedBooks.get(0).getBookType());
        assertEquals("1984", savedBooks.get(1).getTitle());
        assertEquals(BookType.FICTION, savedBooks.get(1).getBookType());
    }

    @Test
    void addBookToLibraryShouldConnectBookAndLibrary() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");

        Library library = new Library();
        library.setId(10L);
        library.setName("Libri");

        when(bookRepository.findBookByTitle("Clean Code")).thenReturn(Optional.of(book));
        when(libraryRepository.findLibraryByName("Libri")).thenReturn(Optional.of(library));

        bookService.addBookToLibrary("Clean Code", "Libri");

        assertTrue(library.getBooks().contains(book));
        assertTrue(book.getLibraries().contains(library));
        verify(libraryRepository).save(library);
    }

    @Test
    void addBookToLibraryShouldThrowExceptionWhenBookIsAlreadyInLibrary() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");

        Library library = new Library();
        library.setId(10L);
        library.setName("Libri");
        library.getBooks().add(book);

        when(bookRepository.findBookByTitle("Clean Code")).thenReturn(Optional.of(book));
        when(libraryRepository.findLibraryByName("Libri")).thenReturn(Optional.of(library));

        assertThrows(
                IllegalStateException.class,
                () -> bookService.addBookToLibrary("Clean Code", "Libri")
        );
    }

    @Test
    void getBooksByTitleShouldReturnBookResponsesWithLibraryNames() {
        Library library = new Library();
        library.setName("Libri");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setBookType(BookType.SCIENCE);
        book.getLibraries().add(library);

        when(bookRepository.findAllByTitleIgnoreCase("Clean Code")).thenReturn(List.of(book));

        List<BookDetailsResponse> result = bookService.getBooksByTitle("Clean Code");

        assertEquals(1, result.size());
        assertEquals("Clean Code", result.get(0).getTitle());
        assertEquals("Robert C. Martin", result.get(0).getAuthor());
        assertEquals("SCIENCE", result.get(0).getBookType());
        assertEquals(List.of("Libri"), result.get(0).getLibraryNames());
    }
}

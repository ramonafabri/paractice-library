package hu.progmasters.firstspringdemo.service;

import hu.progmasters.firstspringdemo.domain.Library;
import hu.progmasters.firstspringdemo.dto.incoming.LibraryCommand;
import hu.progmasters.firstspringdemo.dto.outgoing.LibraryDetails;
import hu.progmasters.firstspringdemo.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private LibraryRepository libraryRepository;

    @InjectMocks
    private LibraryService libraryService;

    @Test
    void createLibraryShouldSaveLibraryWithCommandData() {
        LibraryCommand command = new LibraryCommand("Libri", 10000);

        libraryService.createLibrary(command);

        ArgumentCaptor<Library> libraryCaptor = ArgumentCaptor.forClass(Library.class);
        verify(libraryRepository).save(libraryCaptor.capture());

        Library savedLibrary = libraryCaptor.getValue();
        assertEquals("Libri", savedLibrary.getName());
        assertEquals(10000, savedLibrary.getCapacity());
    }

    @Test
    void getLibraryByNameShouldReturnLibraryDetails() {
        Library library = new Library();
        library.setId(1L);
        library.setName("Readwell");
        library.setCapacity(1000);

        when(libraryRepository.findLibraryByName("Readwell")).thenReturn(Optional.of(library));

        LibraryDetails result = libraryService.getLibraryByName("Readwell");

        assertEquals(1L, result.getId());
        assertEquals("Readwell", result.getName());
        assertEquals(1000, result.getCapacity());
    }
}

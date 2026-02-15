package hu.progmasters.firstspringdemo.controll;


import hu.progmasters.firstspringdemo.domain.Library;
import hu.progmasters.firstspringdemo.dto.incoming.LibraryCommand;
import hu.progmasters.firstspringdemo.dto.outgoing.LibraryDetails;
import hu.progmasters.firstspringdemo.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }


    @PostMapping
    public ResponseEntity<Void> createLibrary(@Valid @RequestBody LibraryCommand libraryCommand) {
        libraryService.createLibrary(libraryCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{name}")
    public ResponseEntity<LibraryDetails> getLibraryByName(@PathVariable String name) {
        LibraryDetails response = libraryService.getHiveByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}

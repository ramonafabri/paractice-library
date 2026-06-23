package hu.progmasters.firstspringdemo.service;


import hu.progmasters.firstspringdemo.domain.Library;
import hu.progmasters.firstspringdemo.dto.incoming.LibraryCommand;
import hu.progmasters.firstspringdemo.dto.outgoing.LibraryDetails;
import hu.progmasters.firstspringdemo.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LibraryService {

    private LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public void createLibrary(LibraryCommand  libraryCommand) {
        Library library = new Library();
                library.setName(libraryCommand.getName());
                library.setCapacity(libraryCommand.getCapacity());
        libraryRepository.save(library);

    }


    public LibraryDetails getLibraryByName(String name) {
        Library library = findLibraryByName(name);
        LibraryDetails libraryDetails = new LibraryDetails();
                libraryDetails.setId(library.getId());
                libraryDetails.setName(library.getName());
                libraryDetails.setCapacity(library.getCapacity());
                return libraryDetails;
    }

    public Library findLibraryByName(String name){
        return libraryRepository.findLibraryByName(name)
                .orElseThrow();
    }
}

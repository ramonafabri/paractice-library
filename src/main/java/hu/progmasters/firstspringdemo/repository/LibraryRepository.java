package hu.progmasters.firstspringdemo.repository;

import hu.progmasters.firstspringdemo.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library,Long> {

    Optional<Library> findLibraryByName(String name);
}

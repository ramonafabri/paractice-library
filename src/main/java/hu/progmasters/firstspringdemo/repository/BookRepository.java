package hu.progmasters.firstspringdemo.repository;

import hu.progmasters.firstspringdemo.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findBookByTitle(String title);

    List<Book> findAllByTitleIgnoreCase(String title);


}

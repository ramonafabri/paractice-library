package hu.progmasters.firstspringdemo.dto.outgoing;


import hu.progmasters.firstspringdemo.domain.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetails {

    private Long id;

    private String title;

    private String author;

    private String bookType;

    private String libraryName;


}

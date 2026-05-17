package hu.progmasters.firstspringdemo.dto.outgoing;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetails {

    private Long id;

    private String title;

    private String author;

    private String bookType;

    private List<String> libraryNames;


}

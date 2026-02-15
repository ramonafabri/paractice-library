package hu.progmasters.firstspringdemo.dto.incoming;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCommand {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    private String author;

    @NotBlank(message = "Book type cannot be empty")
    private String bookType;

    @NotBlank(message = "Library name cannot be empty")
    private String libraryName;

}

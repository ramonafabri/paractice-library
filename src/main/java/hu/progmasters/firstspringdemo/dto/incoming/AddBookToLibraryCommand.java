package hu.progmasters.firstspringdemo.dto.incoming;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBookToLibraryCommand {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Library cannot be empty")
    private String libraryName;

}

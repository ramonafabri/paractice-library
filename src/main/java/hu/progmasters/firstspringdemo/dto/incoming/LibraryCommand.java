package hu.progmasters.firstspringdemo.dto.incoming;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryCommand {


    @NotBlank(message = "Library name cannot be empty")
    private String name;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 100, message = "Capacity must be between 100 and 10.000")
    @Max(value = 10_000, message = "Capacity must be between 100 and 10.000")
    private Integer capacity;


    public String getName() {
        return name;
    }

    public LibraryCommand setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public LibraryCommand setCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    @Override
    public String toString() {
        return "LibraryCommand{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}

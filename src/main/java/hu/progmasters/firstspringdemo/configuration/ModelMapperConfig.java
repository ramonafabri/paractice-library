package hu.progmasters.firstspringdemo.configuration;


import hu.progmasters.firstspringdemo.domain.Book;
import hu.progmasters.firstspringdemo.dto.outgoing.BookDetails;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.createTypeMap(Book.class, BookDetails.class)
                .addMapping(book -> book.getLibrary().getName(), BookDetails::setLibraryName);
//        modelMapper.createTypeMap(Book.class, BookListItem.class)
//                .addMapping(book -> book.getLibrary().getName(), BookListItem :: setLibraryName);
        return modelMapper;
    }



}

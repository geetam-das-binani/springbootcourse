package springbootlearn.springbootlearn.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;



@Data
@AllArgsConstructor
@Document(collection = "config_journal")

public class ConfigJournalApp {

    private String key;
    private String value;

    
}

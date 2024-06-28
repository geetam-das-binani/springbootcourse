package springbootlearn.springbootlearn.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

@Getter
@Setter
@AllArgsConstructor

@Document(collection = "journal")
public class JournalEntry {
    @Id
    private String id;

    @NonNull
    private String title;

    private String content;

    private Date date = new Date();

    public JournalEntry() {
        this.date = new Date();
    }

}

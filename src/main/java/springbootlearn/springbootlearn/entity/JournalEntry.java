package springbootlearn.springbootlearn.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import springbootlearn.springbootlearn.emuns.Sentiment;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Document(collection = "journal")
public class JournalEntry {
    @Id
    private String id;

    @NonNull
    private String title;

    private String content;

    private Date date = new Date();

    private Sentiment sentiment;

    public LocalDateTime getLocalDateTime() {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

}

//! This should fix the issue by allowing you to use isAfter on the LocalDateTime object converted from Date. 
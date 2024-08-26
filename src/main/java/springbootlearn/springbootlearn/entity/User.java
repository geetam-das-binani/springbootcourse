package springbootlearn.springbootlearn.entity;

import java.util.*;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    private List<String> roles;

    private String email;
    
    private boolean sentimentAnalysis; 
    

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}

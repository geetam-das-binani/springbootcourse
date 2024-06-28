package springbootlearn.springbootlearn.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import springbootlearn.springbootlearn.entity.JournalEntry;

@Repository
@Component
public interface JournalRepositiory extends MongoRepository<JournalEntry,String> {
    
}

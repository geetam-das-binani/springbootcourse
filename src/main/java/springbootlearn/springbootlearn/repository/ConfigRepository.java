package springbootlearn.springbootlearn.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import springbootlearn.springbootlearn.entity.ConfigJournalApp;

@Repository
public interface ConfigRepository extends MongoRepository<ConfigJournalApp, ObjectId> {
    
}

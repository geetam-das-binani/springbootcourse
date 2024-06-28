package springbootlearn.springbootlearn.repository;




import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import springbootlearn.springbootlearn.entity.User;

@Repository
@Component
public interface UserRepository extends MongoRepository<User,String> {
    
  public  User findByUserName(String username);
  public  void deleteByUserName(String username);
}

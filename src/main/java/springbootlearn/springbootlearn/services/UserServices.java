package springbootlearn.springbootlearn.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import springbootlearn.springbootlearn.entity.User;
import springbootlearn.springbootlearn.repository.UserRepository;

@Service
@Slf4j
public class UserServices {

    // private static final Logger logger =
    // LoggerFactory.getLogger(UserServices.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;

 

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
           

            User entry = userRepository.save(user);

            return entry;
        } catch (Exception e) {
            // * if we use @Slf4j annotation then we have to use log and no need to create
            // logger instance */
            log.trace("Error occured for {} :", user.getUserName());
            log.debug("Error occured for {} :", user.getUserName());
            log.warn("Error occured for {} :", user.getUserName());
            log.error("Error occured for {} :", user.getUserName());
            // logger.error("Error occured for {} :", user.getUserName());
            // logger.info("Error occured while saving the entry");
            // logger.trace("Error occured while saving the entry");
            // logger.debug("Error occured while saving the entry");
            // logger.warn("Error occured while saving the entry");
            throw new RuntimeException(e.getMessage());
        }
    }

    public User saveAdminUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER", "ADMIN"));

            User entry = userRepository.save(user);

            return entry;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveJournalInUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getSingleUser(String id) {
        return userRepository.findById(id);

    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);

    }

    public boolean deleteOneSingleUserByUsername(String username) {
        try {
            userRepository.deleteByUserName(username);

            return true;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public boolean updateOne(User newUser, String username) {

        User oldUser = userRepository.findByUserName(username);

        if (oldUser != null) {
            oldUser.setUserName(
                    newUser.getUserName() == null || newUser.getUserName().equals("") ? oldUser.getUserName()
                            : newUser.getUserName());
            oldUser.setPassword(
                    newUser.getPassword() == null || newUser.getPassword().equals("") ? oldUser.getPassword()
                            : passwordEncoder.encode(newUser.getPassword()));
oldUser.setSentimentAnalysis(true);
            userRepository.save(oldUser);
            return true;
        }

        return false;

    }

    public List<User> getUserForSentimentAnalysis() {
        try {
            Query query = new Query();
            // query.addCriteria(Criteria.where("userName").is("harry"));
            // query.addCriteria(Criteria.where("email").exists(true));
            // query.addCriteria(Criteria.where("sentimentAnalysis").exists(true));

        //     Criteria criteria = new Criteria();
        // query.addCriteria (
        //          criteria.andOperator
        //         (
        // Criteria.where("email").exists(true),
        // Criteria.where("email").ne(null).ne(""),
        // Criteria.where("email").regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
        //  Criteria.where("sentimentAnalysis").is(true)));

        query.addCriteria(Criteria.where("email").regex("^[\\w\\-\\.]+@([\\w\\-]+\\.)+[\\w\\-]{2,4}$"));
         query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        //  query.addCriteria(Criteria.where("roles").in("USER","ADMIN"));
        //  query.addCriteria(Criteria.where("sentimentAnalysis").type(JsonSchemaObject.Type.BsonType.BOOLEAN));
        
            return mongoTemplate.find(query, User.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
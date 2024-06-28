package springbootlearn.springbootlearn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import springbootlearn.springbootlearn.entity.User;
import springbootlearn.springbootlearn.repository.UserRepository;

@Service
@Component
public class AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserServices userServices;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUserAdmin(User user) {
        try {
            return userServices.saveAdminUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}

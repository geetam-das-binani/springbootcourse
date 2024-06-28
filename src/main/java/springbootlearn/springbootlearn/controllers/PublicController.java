package springbootlearn.springbootlearn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootlearn.springbootlearn.entity.User;
import springbootlearn.springbootlearn.services.UserServices;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
   private UserServices userServices;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "I am healthy";
    }

    @PostMapping("/create-user")
    public ResponseEntity<Boolean> saveUser(@RequestBody User user) {
        try {
            userServices.save(user);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

    }
}

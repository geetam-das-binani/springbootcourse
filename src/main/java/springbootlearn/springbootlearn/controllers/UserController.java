package springbootlearn.springbootlearn.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import springbootlearn.springbootlearn.apiResponse.WeatherResponse;
import springbootlearn.springbootlearn.entity.User;

import springbootlearn.springbootlearn.services.UserServices;
import springbootlearn.springbootlearn.services.WeatherService;
import springbootlearn.springbootlearn.services.EmailService;


@RestController
@RequestMapping("/api/v1/users")

public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getSingleUser(@PathVariable String id) {
        Optional<User> user = userServices.getSingleUser(id);
        return user.isPresent() ? new ResponseEntity<>(user.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean isUpdated = userServices.updateOne(user, username);
        return isUpdated ? new ResponseEntity<>(isUpdated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isDeleted = userServices.deleteOneSingleUserByUsername(username);

            return new ResponseEntity<>(isDeleted, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<String> greeting() {
        try {
            // Authentication authentication =
            // SecurityContextHolder.getContext().getAuthentication();

            return new ResponseEntity<>("weather feels like" + " "
                    + weatherService.getWeatherResponse("Delhi").getWeather()[0].getDescription(), HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sentiment")
    public ResponseEntity<?> getSentiment() {
        try {
            return new ResponseEntity<>(userServices.getUserForSentimentAnalysis(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail() {

        try {
            emailService.sendEmail("geetambinani6@gmail.com", "hello", "test mail");
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }

    @GetMapping("/redis/{city}")
    public ResponseEntity<WeatherResponse> getRedisCache(@PathVariable String city) {
        try {
        //    WeatherResponse w= weatherService.getWeatherResponse(city);
            // WeatherResponse weatherResponse= new WeatherResponse();
            // weatherResponse.setWeather(w.getWeather());
            // weatherResponse.setMain(w.getMain());
            // weatherResponse.setWind(w.getWind());
            // weatherResponse.setCoord(w.getCoord());
            // System.out.println(city);
            return new ResponseEntity<>(weatherService.getWeatherResponse(city), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

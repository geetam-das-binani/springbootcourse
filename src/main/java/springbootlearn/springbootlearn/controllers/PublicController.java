package springbootlearn.springbootlearn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import springbootlearn.springbootlearn.apiResponse.Posts;
import springbootlearn.springbootlearn.entity.User;
import springbootlearn.springbootlearn.services.CustomUserDetailsServiceImpl;
import springbootlearn.springbootlearn.services.UserServices;
import springbootlearn.springbootlearn.utils.JwtUtil;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsServiceImpl;
    
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "I am healthy";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> saveUser(@RequestBody User user) {
        try {
            userServices.save(user);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
          authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

                   
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUserName());
            
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            

            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception occured while creating Authentication Token");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Unauthorized");
        }

    }

    @PostMapping("/create")
    public ResponseEntity<Posts> save(@RequestBody Posts post) {
        Posts posts = new Posts(post.getTitle(), post.getBody(), post.getUseriD());
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-type", "application/json");
            HttpEntity<Posts> httpEntity = new HttpEntity<>(posts, httpHeaders);
            ResponseEntity<Posts> responseEntity = restTemplate.exchange(
                    "https://jsonplaceholder.typicode.com/posts",
                    HttpMethod.POST,
                    httpEntity,
                    Posts.class);

            return responseEntity;

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

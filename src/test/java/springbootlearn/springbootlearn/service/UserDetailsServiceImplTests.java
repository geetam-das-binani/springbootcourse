package springbootlearn.springbootlearn.service;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


import springbootlearn.springbootlearn.repository.UserRepository;
import springbootlearn.springbootlearn.services.CustomUserDetailsServiceImpl;

import static org.mockito.Mockito.*;

import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
public class UserDetailsServiceImplTests {

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    // @Mock
    // private UserRepository userRepository;

    void loadUserByUserNameTest() {
        // when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().username("shyam").password("shyam").build());

        // UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsername("shyam");
    }
}

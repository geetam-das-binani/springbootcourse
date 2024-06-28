package springbootlearn.springbootlearn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import springbootlearn.springbootlearn.entity.User;
import springbootlearn.springbootlearn.repository.UserRepository;

@SpringBootTest
public class UserServicesTests {

    @Autowired
    private UserRepository userrepository;

    @Test
    @ValueSource(strings = {
            "anuj",
            "shyam"
    })
    @Disabled
    public void testFindByUserName(String name) {
        assertEquals(4, 4);
        assertNotNull(userrepository.findByUserName(name));
        assertTrue(5 > 3);
        User user = userrepository.findByUserName(name);
        assertTrue(!user.getJournalEntries().isEmpty(), "User Journal Entry is empty");

    }

    @ParameterizedTest
    @Disabled
    @CsvSource({
            "1,2,3",
            "4,5,9",
            "6,7,13"
    })

    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b, "Test failed, expected:" + expected + " actual:" + (a + b));
    }
}

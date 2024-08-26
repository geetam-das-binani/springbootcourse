package springbootlearn.springbootlearn.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posts {
    
    private String title;
    private String body;
    private int useriD;
}



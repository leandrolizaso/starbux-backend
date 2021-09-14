import com.starbux.StarbuxApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {StarbuxApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApplicationTest {

    @Test
    void contextLoads(){}
}

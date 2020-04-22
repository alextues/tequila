package progforce.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableWebMvc
public class TequilaApplication implements CommandLineRunner {
    @Autowired
    private Prelude prelude;

    public static void main(String[] args) {
        SpringApplication.run(TequilaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        prelude.testUsersTable();
        prelude.testChannelTable();
    }
}

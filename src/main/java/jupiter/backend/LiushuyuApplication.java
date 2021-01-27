package jupiter.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@SpringBootApplication()
@EnableMongoAuditing
public class LiushuyuApplication {

	public static void main(String[] args) {

		SpringApplication.run(LiushuyuApplication.class, args);
	}

}

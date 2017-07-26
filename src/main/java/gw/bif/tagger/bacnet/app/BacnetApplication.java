package gw.bif.tagger.bacnet.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("gw.bif.tagger.bacnet")
@EntityScan("cgw.bif.tagger.bacnet")
public class BacnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BacnetApplication.class, args);
	}
}

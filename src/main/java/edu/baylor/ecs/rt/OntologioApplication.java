package edu.baylor.ecs.rt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class OntologioApplication {

    public static void main(String[] args) {
        SpringApplication.run(OntologioApplication.class, args);
    }

}

package pl.iledasz;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "pl.iledasz")
public class Application {

    public static void main(String[] args) {

        new SpringApplicationBuilder(Application.class).run(args);
    }
}

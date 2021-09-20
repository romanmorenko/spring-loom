package ru.neoflex.springloom;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication
@EnableOpenApi
public class SpringLoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLoomApplication.class, args);
    }

}

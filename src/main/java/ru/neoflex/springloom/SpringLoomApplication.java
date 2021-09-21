package ru.neoflex.springloom;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication
@EnableOpenApi
public class SpringLoomApplication {

    public static final String THREADS_VIRTUAL = "threads.virtual";

    public static void main(String[] args) {
        System.setProperty("jdk.defaultScheduler.parallelism", "200");
        System.setProperty("jdk.defaultScheduler.maxPoolSize", "254");
        System.setProperty(THREADS_VIRTUAL, "true");
        SpringApplication.run(SpringLoomApplication.class, args);
    }

}

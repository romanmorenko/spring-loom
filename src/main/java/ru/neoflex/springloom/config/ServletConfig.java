package ru.neoflex.springloom.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class ServletConfig {

    @Bean
    public TomcatServletWebServerFactory configurableTomcatWebServerFactory() {

        return new CustomTomcatServletWebServerFactory();
    }

    @Bean
    public WebServerFactoryCustomizer ajpContainerCustomizer(Environment environment,
                                                             ServerProperties serverProperties) {
        return new CustomTomcatWebServerFactoryCustomizer(environment, serverProperties);
    }
}

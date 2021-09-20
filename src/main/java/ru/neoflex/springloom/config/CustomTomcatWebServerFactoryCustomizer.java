package ru.neoflex.springloom.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.embedded.TomcatWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.core.env.Environment;


public class CustomTomcatWebServerFactoryCustomizer extends TomcatWebServerFactoryCustomizer {

    public CustomTomcatWebServerFactoryCustomizer(Environment environment,
                                                  ServerProperties serverProperties) {
        super(environment, serverProperties);
    }

    @Override
    public void customize(ConfigurableTomcatWebServerFactory factory) {

        super.customize(factory);
    }
}

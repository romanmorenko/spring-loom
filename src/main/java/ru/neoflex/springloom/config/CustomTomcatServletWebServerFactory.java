package ru.neoflex.springloom.config;



import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import java.io.File;

import static ru.neoflex.springloom.SpringLoomApplication.THREADS_VIRTUAL;


public class CustomTomcatServletWebServerFactory extends TomcatServletWebServerFactory {



   @Override
   public WebServer getWebServer(ServletContextInitializer... initializers) {
       if ("false".equals(System.getProperty(THREADS_VIRTUAL))){
           return super.getWebServer(initializers);
       }
       Http11NioProtocol protocol = new Http11NioProtocol();
       protocol.setExecutor(new VirtualThreadExecutor());
       Tomcat tomcat = new Tomcat();
       File baseDir =  createTempDir("tomcat");
       tomcat.setBaseDir(baseDir.getAbsolutePath());
       Connector connector = new Connector(protocol);
       connector.setThrowOnFailure(true);
       tomcat.getService().addConnector(connector);
       customizeConnector(connector);
       tomcat.setConnector(connector);
       tomcat.getHost().setAutoDeploy(false);
       prepareContext(tomcat.getHost(), initializers);
       return getTomcatWebServer(tomcat);
    }
}

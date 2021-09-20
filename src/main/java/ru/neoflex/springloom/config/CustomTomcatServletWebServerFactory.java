package ru.neoflex.springloom.config;


import org.apache.catalina.Executor;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CustomTomcatServletWebServerFactory extends TomcatServletWebServerFactory {


    @Override
    public WebServer getWebServer(ServletContextInitializer... initializers) {

        TomcatWebServer server = (TomcatWebServer) super.getWebServer(initializers);
        Tomcat tomcat = server.getTomcat();
        Http11NioProtocol protocol = new Http11NioProtocol();
        final ExecutorService executorService = Executors.newVirtualThreadExecutor();
        protocol.setExecutor(new Executor() {

            @Override
            public void addLifecycleListener(LifecycleListener listener) {

                System.out.println(listener.toString());
            }

            @Override
            public LifecycleListener[] findLifecycleListeners() {

                return new LifecycleListener[0];
            }

            @Override
            public void removeLifecycleListener(LifecycleListener listener) {

                System.out.println(listener.toString());
            }

            @Override
            public void init() throws LifecycleException {

                System.out.println("Executor service init");
            }

            @Override
            public void start() throws LifecycleException {

            }

            @Override
            public void stop() throws LifecycleException {

            }

            @Override
            public void destroy() throws LifecycleException {

                System.out.println("Executor service destroy");
                executorService.close();
            }

            @Override
            public LifecycleState getState() {

                return null;
            }

            @Override
            public String getStateName() {

                return null;
            }

            @Override
            public void execute(Runnable command) {

                executorService.execute(command);
            }

            @Override
            public String getName() {

                return null;
            }

            @Override
            public void execute(Runnable command, long timeout, TimeUnit unit) {

                executorService.execute(command);
            }
        });
        Connector connector = new Connector(protocol);
        connector.setPort(8083);
        tomcat.getService().addConnector(connector);
        return server;
    }
}

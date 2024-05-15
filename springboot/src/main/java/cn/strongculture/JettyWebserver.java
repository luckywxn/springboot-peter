package cn.strongculture;

import org.springframework.web.context.WebApplicationContext;

public class JettyWebserver implements WebServer{
    @Override
    public void start(WebApplicationContext applicationContext) {
        System.out.println("Jetty Webserver Start");
    }
}

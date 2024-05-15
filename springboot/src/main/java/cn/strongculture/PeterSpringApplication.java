package cn.strongculture;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Map;

public class PeterSpringApplication {
    public static void run(Class clazz) {
        //创建一个Spring容器
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(clazz);
        applicationContext.refresh();
        //启动Tomcat Jetty
//        startTomcat(applicationContext);
        WebServer webServer = getWebServer(applicationContext);
        webServer.start(applicationContext);
    }

    private static WebServer getWebServer(AnnotationConfigWebApplicationContext applicationContext) {
        Map<String, WebServer> webServers = applicationContext.getBeansOfType(WebServer.class);
        if (webServers.size() == 0){
            throw new NullPointerException();
        }
        if (webServers.size() > 1){
            throw new IllegalStateException();
        }
        return webServers.values().iterator().next();
    }


}

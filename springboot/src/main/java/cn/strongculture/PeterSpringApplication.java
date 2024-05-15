package cn.strongculture;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class PeterSpringApplication {
    public static void run(Class clazz) {
        //创建一个Spring容器
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(clazz);
        applicationContext.refresh();
        //启动Tomcat Jetty
        startTomcat(applicationContext);
    }

    private static void startTomcat(WebApplicationContext applicationContext) {
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");

        Connector connector = new Connector();
        connector.setPort(8081);

        Engine engine = tomcat.getEngine();
        engine.setDefaultHost("localhost");

        Host host = tomcat.getHost();
        host.setName("localhost");

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        service.addConnector(connector);

        tomcat.addServlet(contextPath, "dispatcherServlet", new DispatcherServlet(applicationContext));
        context.addServletMappingDecoded("/*", "dispatcherServlet");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}

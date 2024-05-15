package cn.strongculture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerAutoConfiguration {

    @Bean
//    @Conditional(TomcatCondition.class)
    @PeterConditionOnClass("org.apache.catalina.startup.Tomcat")
    public TomcatWebserver tomcatWebserver(){
        return new TomcatWebserver();
    }

    @Bean
//    @Conditional(JettyCondition.class)
    @PeterConditionOnClass("org.eclipse.jetty.server.Server")
    public JettyWebserver jettyWebserver(){
        return new JettyWebserver();
    }

}

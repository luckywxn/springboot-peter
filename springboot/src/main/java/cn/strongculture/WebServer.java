package cn.strongculture;

import org.springframework.web.context.WebApplicationContext;

public interface WebServer {
    void start(WebApplicationContext applicationContext);
}

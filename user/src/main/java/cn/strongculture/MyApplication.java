package cn.strongculture;

import org.springframework.context.annotation.Import;

@PeterSpringBootApplication
@Import(WebServerAutoConfiguration.class)
public class MyApplication {
    public static void main(String[] args) {
        PeterSpringApplication.run(MyApplication.class);
    }
}

package de.test.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
//@EnableCaching(proxyTargetClass = true)
@ComponentScan(basePackages = "de.test")
public class ClientApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(ClientApplication.class)
                .run(args);
    }


    @PostConstruct
    public void setProperty() {
        System.setProperty("wicket.ioc.useByteBuddy", "true");
    }
}

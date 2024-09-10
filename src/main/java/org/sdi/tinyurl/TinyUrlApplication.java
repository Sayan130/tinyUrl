package org.sdi.tinyurl;

import org.sdi.tinyurl.keygenerationservice.GenerateKeys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TinyUrlApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TinyUrlApplication.class, args);
        GenerateKeys generateKeys = new GenerateKeys();
        generateKeys.generateKeys(100);
    }

}

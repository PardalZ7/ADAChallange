package br.com.ada.challange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class PropertiesConfig {

    @Bean
    public static Properties generalProperties() throws IOException {

        Properties props = new Properties();
        FileInputStream file = new FileInputStream("src/main/resources/application.properties");
        props.load(file);
        return props;

    }

}

package uz.pdp.appideabot;

import org.apache.commons.codec.CharEncoding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class AppIdeaBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppIdeaBotApplication.class, args);
    }

    @Bean
    public ResourceBundleMessageSource messageSourceResourceBundle() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("classpath:messages");
        resourceBundleMessageSource.setFallbackToSystemLocale(false);
        resourceBundleMessageSource.setDefaultEncoding(CharEncoding.UTF_8);
        return resourceBundleMessageSource;
    }
}

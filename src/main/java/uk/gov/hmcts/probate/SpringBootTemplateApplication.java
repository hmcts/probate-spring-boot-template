package uk.gov.hmcts.probate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringBootTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTemplateApplication.class, args);
    }
}
                                                                              
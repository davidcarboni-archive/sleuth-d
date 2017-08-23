package uk.gov.ros.sleuthd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class Service {

    private static Logger log = LoggerFactory.getLogger(Service.class);

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private SpanAccessor spanAccessor;

    @RequestMapping("/")
    String service() {
        log.info(appName + " called");

        log.debug(appName + " did a thing.");

        log.info(appName + " finishing up.");

        return new Result(appName, this.spanAccessor.getCurrentSpan()).toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }
}

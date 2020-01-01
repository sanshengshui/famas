package com.grozacloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author james mu
 * @date 2019/12/29 18:20
 */
@SpringBootApplication(scanBasePackages = {"com.grozacloud"})
public class GrozaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrozaServerApplication.class);
    }

}

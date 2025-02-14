package org.wjj.qrcpcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class QrcpcheckmanApplication {
    public static void main(String[] args) {
        SpringApplication.run(QrcpcheckmanApplication.class, args);
    }
}

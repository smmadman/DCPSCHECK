package org.wjj.qrcpcheck;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableAsync
@MapperScan
public class QrcpcheckmanApplication {
    public static void main(String[] args) {
        SpringApplication.run(QrcpcheckmanApplication.class, args);
    }
}

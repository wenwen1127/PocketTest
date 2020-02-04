package com.pkt;


import com.pkt.Config.SpringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@MapperScan(basePackages = {"com.pkt.Dao"})
@SpringBootApplication(scanBasePackages="com.pkt")
@Import(SpringUtils.class)
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class,args);
    }
}

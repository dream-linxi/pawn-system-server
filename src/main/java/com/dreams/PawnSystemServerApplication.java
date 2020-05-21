package com.dreams;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.dreams.sys.dao","com.dreams.base.dao","com.dreams.product.dao"})
public class PawnSystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PawnSystemServerApplication.class, args);
    }

}

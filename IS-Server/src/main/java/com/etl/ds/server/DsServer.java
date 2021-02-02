package com.etl.ds.server;

import com.cvte.psd.conf.core.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//mapperscan是链接JDBC进行查询数据使用
//如果存在多个datasource可以参考资产地图
@MapperScan("com.etl.ds.core.mapper")
@SpringBootApplication(scanBasePackages = "com.etl.ds")
@EnableApolloConfig
public class DsServer {
    public static void main(String[] args) {
        SpringApplication.run(DsServer.class, args);
    }
}
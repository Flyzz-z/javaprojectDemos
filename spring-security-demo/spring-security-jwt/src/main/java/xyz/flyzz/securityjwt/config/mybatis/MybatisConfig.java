package xyz.flyzz.securityjwt.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("xyz.flyzz.securityjwt.mapper")
public class MybatisConfig {
}

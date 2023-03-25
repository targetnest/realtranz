package com.unr.realtranz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:20-01-2023 01:09
 **/
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class RealTranzPersistenceConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new RealTranzAuditConfig();
    }
}

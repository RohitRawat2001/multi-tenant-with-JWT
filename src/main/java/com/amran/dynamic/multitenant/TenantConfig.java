package com.amran.dynamic.multitenant;


import com.amran.dynamic.multitenant.security.UserTenantInformation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
public class TenantConfig {

    @Bean
    @ApplicationScope
    public UserTenantInformation userTenantInformation() {
        return new UserTenantInformation();
    }

}
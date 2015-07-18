package com.wyc.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.wyc.ApplicationContextProvider;
import com.wyc.defineBean.ApplicationProperties;
import com.wyc.wx.domain.WxContext;

@Configuration
@ComponentScan(basePackages = "com.wyc", excludeFilters = {
        @Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebConfig.class),
        @Filter(type = FilterType.ASSIGNABLE_TYPE, value = DatabaseConfig.class) })
public class AppConfig {
    final static Logger logger = LoggerFactory.getLogger(AppConfig.class);
    @Bean
    public ApplicationContextProvider applicationContextProvider() {
        return new ApplicationContextProvider();
    }

    @Bean
    public ApplicationProperties applicationProperties() {
        ApplicationProperties properties = new ApplicationProperties();
        File databaseConfigFile = new File(
                "/etc/onlineRetailers/application.properties");

        try {
            if (databaseConfigFile.exists()) {
                properties.load(new FileInputStream(databaseConfigFile));
            } else {
                InputStream defaultConfig = this.getClass()
                        .getResourceAsStream("/application.properties");
                properties.load(defaultConfig);
            }
        } catch (IOException e) {
            logger.error("Load application.properties error: {}", e);
        }
        return properties;
    }
    
    @Bean
    @Autowired
    public WxContext wxContext(ApplicationProperties myProperties){
        WxContext wxContext = new WxContext();
        wxContext.setAppid(myProperties.getProperty("appid"));
        wxContext.setAppsecret(myProperties.getProperty("appsecret"));
        wxContext.setFilePath(myProperties.getProperty("file_path"));
        return wxContext;
    }
}

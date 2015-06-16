package com.wyc.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@EnableWebMvc
@Configuration
@ComponentScan(basePackages="com.wyc",
				excludeFilters={
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=AppConfig.class)
})
public class WebConfig extends WebMvcConfigurerAdapter{
	
}

package com.wyc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages="com.wyc",
				excludeFilters={
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=WebConfig.class),
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=DatabaseConfig.class)
})
public class AppConfig {
	public AppConfig() {
		System.out.println("hahahaha");
	}
}

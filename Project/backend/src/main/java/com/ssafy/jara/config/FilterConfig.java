package com.ssafy.jara.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.ssafy.jara.filter.JsonFilter;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

	// Naver Lucy XSS Filter bean 등록
	@Bean
    public FilterRegistrationBean<XssEscapeServletFilter> getFilterRegistraionBean() {
    	FilterRegistrationBean<XssEscapeServletFilter> registraionBean = new FilterRegistrationBean<XssEscapeServletFilter>();
    	registraionBean.setFilter(new XssEscapeServletFilter());
    	registraionBean.setOrder(1);
    	registraionBean.addUrlPatterns("/*");
    	
    	return registraionBean;
    }
	
	// Json XSS Filter bean 등록
	@Bean
	public FilterRegistrationBean<JsonFilter> getJsonFilterRegistrationBean() {
		FilterRegistrationBean<JsonFilter> registrationBean = new FilterRegistrationBean<JsonFilter>(new JsonFilter());
		registrationBean.setOrder(2);
		registrationBean.addUrlPatterns("/*");
		
		return registrationBean;
	}
	
}

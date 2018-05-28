package ua.com.company.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScans({ @ComponentScan("ua.com.company.controller"), @ComponentScan("ua.com.company.component") })
@Import({ ThymeLeafConfig.class, DataBaseConfig.class })
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	private static final String UTF8 = "UTF-8";

	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasenames("classpath:customMessages/");
		source.setDefaultEncoding(UTF8);
		source.setUseCodeAsDefaultMessage(true);

		return source;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/style/**").addResourceLocations("/WEB-INF/static/");
	}

}

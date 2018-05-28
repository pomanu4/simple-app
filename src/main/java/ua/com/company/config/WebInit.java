package ua.com.company.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInit implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(ApplicationConfig.class);

		servletContext.addListener(new ContextLoaderListener(webApplicationContext));
		DispatcherServlet servlet = new DispatcherServlet(webApplicationContext);
		ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcherServlet", servlet);
		dynamic.addMapping("/");
		dynamic.setLoadOnStartup(1);
		dynamic.setMultipartConfig(new MultipartConfigElement("", 10000000, 10000000, 10000000));
	}

}

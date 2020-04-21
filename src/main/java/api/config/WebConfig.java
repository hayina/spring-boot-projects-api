package api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig implements WebMvcConfigurer {


	
//	@Bean(name = "multipartResolver")
//	public MultipartResolver multipartResolver() {
//	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//	    multipartResolver.setMaxUploadSize(30000000); // -1
//	    return multipartResolver;
//	}
   
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry
//			.addResourceHandler("/REACT-APP/**")
//			.addResourceLocations("/REACT-APP/")
//		;
//	}
	

}

package api.security;

import api.dao.interfaces.IUserDao;
import api.security.filters.UserRolesFilter;
import api.security.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import api.config.SpringApplicationContext;
import api.dao.UserDao;
import api.security.filters.JwtAuthenticationFilter;
import api.security.filters.JwtAuthorizationFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired private IUserDao userDetailsService;

	@Autowired Environment environment;



	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
				.logout().disable()
				.formLogin().disable()
				.httpBasic().disable();

		http.authorizeRequests()
				.antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
//				.antMatchers("/api/**").authenticated()
//				.antMatchers("/**").permitAll()
			.and()

				.addFilterBefore(getUserRolesFilter(), AnonymousAuthenticationFilter.class)
				.addFilterBefore(getJwtAuthenticationFilter(), UserRolesFilter.class)

//				.addFilter(getUserRolesFilter())
//				.addFilter(getJwtAuthenticationFilter())

//				.exceptionHandling()
//				.authenticationEntryPoint((request, response, exception) -> {
//					HttpUtils.jsonExceptionResponse(response, exception, 401);
//				})
//				.accessDeniedHandler((request, response, exception) -> {
//					HttpUtils.jsonExceptionResponse(response, exception, 403);
//				})

			;

		http.
			sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
	// @formatter:on

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService);
//		auth.userDetailsService(SpringApplicationContext.getBean(IUserDao.class));
	}

	@Bean
	public JwtAuthenticationFilter getJwtAuthenticationFilter() throws Exception {

		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/api/login");
		filter.setAuthenticationFailureHandler((request, response, exception) -> {
			HttpUtils.jsonExceptionResponse(response, exception, 401);
		});
		return filter;
	}




	@Bean
	public UserRolesFilter getUserRolesFilter() {
		return new UserRolesFilter();
	}
//
// 	@Bean
//	public JwtAuthorizationFilter getJwtAuthorizationFilter() throws Exception {
//		return new JwtAuthorizationFilter(authenticationManager());
//	}
	
	@Bean
	public SpringApplicationContext SpringApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

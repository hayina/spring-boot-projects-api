package api.security.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import api.beans.LoginBean;
import api.entities.User;
import api.security.models.UserPrincipal;
import api.security.utils.HttpUtils;
import api.services.JwtService;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private @Autowired Environment env;
	private @Autowired JwtService jwtService;
//	private AuthenticationManager authenticationManager;

	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//		super("/**");
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {


		System.out.println("attemptAuthentication");
		
		try {
			LoginBean credentials = new ObjectMapper().readValue(request.getInputStream(), LoginBean.class);
			
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(credentials.login, credentials.password)
			);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		
		UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
		User userEntity = principal.getUserEntity();
		
		String rolesForJwtClaim = principal.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

		List<String> rolesForResponse = principal.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
				
		String token = jwtService.generateToken(userEntity.getLogin(), userEntity.getId(), rolesForJwtClaim);
		
		response.setHeader(
				env.getProperty("security.token.header-string"),
				env.getProperty("security.token.prefix") + " " + token
		);

//		response.setHeader("Mama", "Mama");
//		response.setHeader("baba", "baba");

		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("id", userEntity.getId());
		userInfo.put("login", userEntity.getLogin());
		userInfo.put("email", userEntity.getEmail());
		userInfo.put("nom", userEntity.getNom());
		userInfo.put("prenom", userEntity.getPrenom());
		
		String prefix = env.getProperty("security.role.prefix");
		userInfo.put("roles", rolesForResponse.stream().filter(r -> r.startsWith(prefix)).collect(Collectors.toList()));
		userInfo.put("permissions", rolesForResponse.stream().filter(r -> !r.startsWith(prefix)).collect(Collectors.toList()));
		
		
		HttpUtils.jsonResponse(response, userInfo);
	    		


	}

}

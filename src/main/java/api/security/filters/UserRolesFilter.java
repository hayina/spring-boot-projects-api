package api.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserRolesFilter extends OncePerRequestFilter {

    private @Autowired Environment env;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String roles = request.getHeader(env.getProperty("security.user.roles.header-name"));
        String userId = request.getHeader(env.getProperty("security.user.identifier.header-name"));

        if( roles != null && userId != null ) {

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())
                    )
            );

        }


        filterChain.doFilter(request, response);


    }
}

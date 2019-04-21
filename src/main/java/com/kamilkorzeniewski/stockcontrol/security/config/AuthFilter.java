package com.kamilkorzeniewski.stockcontrol.security.config;

import com.kamilkorzeniewski.stockcontrol.security.token.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AuthFilter extends AbstractAuthenticationProcessingFilter {

    private static final String TOKEN_HEADER = "x-auth-token";

    AuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final String token = getTokenValue((HttpServletRequest) req);

        if (StringUtils.isEmpty(token)) {
            chain.doFilter(req, res);
            return;
        }

        this.setAuthenticationSuccessHandler((request, response, authentication) -> chain.doFilter(request, response));
        super.doFilter(req, res, chain);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        final String tokenValue = getTokenValue(httpServletRequest);

        if (StringUtils.isEmpty(tokenValue)) {
            return null;
        }

        Token token = new Token(tokenValue);
        token.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));

        return this.getAuthenticationManager().authenticate(token);

    }


    private String getTokenValue(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames()).stream()
                .filter(header -> header.equalsIgnoreCase(TOKEN_HEADER))
                .map((header -> request.getHeader(header)))
                .findFirst()
                .orElse(null);
    }
}

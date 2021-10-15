package com.gmail.creativegeeksuresh.ishare.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.creativegeeksuresh.ishare.security.CustomUserDetailsService;
import com.gmail.creativegeeksuresh.ishare.service.UserService;
import com.gmail.creativegeeksuresh.ishare.service.util.CustomJwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    CustomJwtService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String reqHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (reqHeader == null || !reqHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = reqHeader.split(" ")[1].trim();
        try {
            Map<String, Object> jwtPayloadData = jwtService.verifyJwtTokenAndGetValue(jwtToken);
            if (jwtPayloadData.size() == 0 || jwtPayloadData.get("username") == null) {
                filterChain.doFilter(request, response);
                return;
            } else {
                UserDetails userDetails = userDetailsService
                        .loadUserByUsername((String) jwtPayloadData.get("username"));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails == null ? List.of() : userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            filterChain.doFilter(request, response);
            return;
        }

    }

}

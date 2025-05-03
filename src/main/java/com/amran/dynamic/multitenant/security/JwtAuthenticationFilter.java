package com.amran.dynamic.multitenant.security;

import com.amran.dynamic.multitenant.constant.JWTConstants;
import com.amran.dynamic.multitenant.mastertenant.config.DBContextHolder;
import com.amran.dynamic.multitenant.mastertenant.entity.MasterTenant;
import com.amran.dynamic.multitenant.mastertenant.service.MasterTenantService;
import com.amran.dynamic.multitenant.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    MasterTenantService masterTenantService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain){
        try {
            String header = httpServletRequest.getHeader(JWTConstants.HEADER_STRING);
            String username = null;
            String audience = null; //tenantOrClientId
            String authToken = null;
            if (header != null && header.startsWith(JWTConstants.TOKEN_PREFIX)) {
                authToken = header.replace(JWTConstants.TOKEN_PREFIX,"");
                try {
                    username = jwtTokenUtil.getUsernameFromToken(authToken);
                    audience = jwtTokenUtil.getAudienceFromToken(authToken);
                    MasterTenant masterTenant = masterTenantService.findByClientId(Integer.valueOf(audience));
                    if(null == masterTenant){
                        logger.error("An error during getting tenant name");
                        throw new BadCredentialsException("Invalid tenant and user.");
                    }
                    DBContextHolder.setCurrentDb(masterTenant.getDbName());
                } catch (IllegalArgumentException ex) {
                    logger.error("An error during getting username from token", ex);
                } catch (ExpiredJwtException ex) {
                    logger.warn("The token is expired and not valid anymore", ex);
                } catch(SignatureException ex){
                    logger.error("Authentication Failed. Username or Password not valid.",ex);
                }
            } else {
                logger.warn("Couldn't find bearer string, will ignore the header");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {

                    Map<String, Object> loggedInUserDetails  = new HashMap<>();
                    loggedInUserDetails.put("userId",userDetails);
                    loggedInUserDetails.put("orgIdx",audience);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    logger.info("authenticated user " + username + ", setting security context");
                    authentication.setDetails(loggedInUserDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
            try {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (Exception e) {
                logger.error("error while filter ",e);
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            logger.error("error while filter ",e);
            throw new RuntimeException(e);
        }
    }
}

package com.amran.dynamic.multitenant.security;

import com.amran.dynamic.multitenant.constant.JWTConstants;
import com.amran.dynamic.multitenant.tenant.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoggedInUserDetails {


    public UserDetails getUser(){

        if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null &&  SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            Map<String,Object> loggedInUser = new HashMap<>();
            try {
                loggedInUser = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
                return  loggedInUser.get("userId") == null ?  null : (UserDetails) loggedInUser.get("userId");
            }catch (Exception e){
                return  null;
            }
        }else{
            return null;
        }
    }


    public String getOrg(){

        if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null &&  SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            Map<String,Object> loggedInUser = new HashMap<>();
            try {
                loggedInUser = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
                return loggedInUser.get("orgIdx") != null ? loggedInUser.get("orgIdx").toString() : null;
            }catch (Exception e){
                return  null;
            }
        }else{
            return null;
        }
    }


    public String getTenantId() {
        try {
            String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            Claims claims = Jwts.parser()
                    .setSigningKey(JWTConstants.SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getAudience();
        } catch (Exception e) {
            return null;
        }
    }
}

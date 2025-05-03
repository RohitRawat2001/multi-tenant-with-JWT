package com.amran.dynamic.multitenant.controller;

import com.amran.dynamic.multitenant.dto.AuthResponse;
import com.amran.dynamic.multitenant.dto.UserLoginDTO;
import com.amran.dynamic.multitenant.mastertenant.config.DBContextHolder;
import com.amran.dynamic.multitenant.mastertenant.entity.MasterTenant;
import com.amran.dynamic.multitenant.mastertenant.service.MasterTenantService;
import com.amran.dynamic.multitenant.security.UserTenantInformation;
import com.amran.dynamic.multitenant.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final Map<String, String> mapValue = new HashMap<>();
    private final Map<String, String> userDbMap = new HashMap<>();



    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final MasterTenantService masterTenantService;
    private final UserTenantInformation userTenantInformation;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, MasterTenantService masterTenantService, UserTenantInformation userTenantInformation) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.masterTenantService = masterTenantService;
        this.userTenantInformation = userTenantInformation;
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginDTO userLoginDTO) throws AuthenticationException {
        LOGGER.info("userLogin() method call...");
        if(null == userLoginDTO.getUserName() || userLoginDTO.getUserName().isEmpty()){
            return new ResponseEntity<>("User name is required", HttpStatus.BAD_REQUEST);
        }
        //set database parameter
        MasterTenant masterTenant = masterTenantService.findByClientId(userLoginDTO.getTenantOrClientId());
        if(null == masterTenant){
            throw new RuntimeException("Please contact service provider.");
        }
        //Entry Client Wise value dbName store into bean.
        loadCurrentDatabaseInstance(masterTenant.getDbName(), userLoginDTO.getUserName());
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUserName(), userLoginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails.getUsername(),String.valueOf(userLoginDTO.getTenantOrClientId()));
        //Map the value into applicationScope bean
        updateUserTenantInfo();
        return ResponseEntity.ok(new AuthResponse(userDetails.getUsername(),token));
    }

    private void loadCurrentDatabaseInstance(String databaseName, String userName) {
        DBContextHolder.setCurrentDb(databaseName);
        mapValue.put(userName, databaseName);
    }

    private void updateUserTenantInfo() {
        if (!mapValue.isEmpty()) {
            for (Map.Entry<String, String> entry : mapValue.entrySet()) {
                String key = entry.getKey();
                if (!userDbMap.containsKey(key)) {
                    userDbMap.putAll(mapValue);
                } else {
                    userDbMap.put(key, mapValue.get(key));
                }
            }
            mapValue.clear();
        }
        userTenantInformation.setMap(userDbMap);
    }


}

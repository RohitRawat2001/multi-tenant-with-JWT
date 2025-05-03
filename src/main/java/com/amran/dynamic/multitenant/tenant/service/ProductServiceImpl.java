package com.amran.dynamic.multitenant.tenant.service;

import com.amran.dynamic.multitenant.security.LoggedInUserDetails;
import com.amran.dynamic.multitenant.tenant.entity.Product;
import com.amran.dynamic.multitenant.tenant.entity.User;
import com.amran.dynamic.multitenant.tenant.repository.ProductRepository;
import com.amran.dynamic.multitenant.tenant.repository.UserRepository;
import com.amran.dynamic.multitenant.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private LoggedInUserDetails loggedInUserDetails;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;


     @Override
     public Product createProduct1(Product product) {
//         // Get the authentication object from the SecurityContext
//         var authentication = SecurityContextHolder.getContext().getAuthentication();
//
//         // Check if authentication object is available
//         if (authentication != null && authentication.getCredentials() != null) {
//             String token = authentication.getCredentials().toString();
//
//             // Extract username and tenantId from the token
//             String username = jwtTokenUtil.extractUsername(token);
//             String tenantId = jwtTokenUtil.extractTenantId(token);
//
//             // Set the username and tenantId in the product object
//             if (username != null) {
//                 product.setCreateBy(username);
//             }
//
//             if (tenantId != null) {
//                 product.setOrgIdx(tenantId);
//             }


         Integer username = userRepository.findByUserName(loggedInUserDetails.getUser().getUsername()).getUserId();
         User username1 = userRepository.findByUserName(loggedInUserDetails.getUser().getUsername());
         String orgId = loggedInUserDetails.getOrg();

         if(username != null) {
             product.setCreateBy(username);
         }

         if(username1 != null){
             product.setCreateByName(username1.getFullName());
         }

         if(orgId != null) {
             product.setOrgIdx(orgId);
         }
             return productRepository.save(product);
     }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}

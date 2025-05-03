package com.amran.dynamic.multitenant.tenant.service;

import com.amran.dynamic.multitenant.tenant.entity.Product;

import java.util.List;


public interface ProductService {

   // Product createProudct(Product product);

    List<Product> getAllProduct();
    Product createProduct1(Product product);
}

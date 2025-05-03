package com.amran.dynamic.multitenant.tenant.entity;

import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "tbl_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    
    @Column(name = "product_name",nullable = false)
    private String productName;

    @Column(name = "createBy")
    private Integer createBy;

    @Column(name = "org_idx")
    private String orgIdx;
   
    @Column(name = "quantity",nullable = false)
    private Integer quantity;
   
    @Column(name = "size",nullable = false,unique = true)
    private String size;

    @Column(name = "createByName")
    private String createByName;

    public Integer getCreateBy() {
        return createBy;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getOrgIdx() {
        return orgIdx;
    }

    public void setOrgIdx(String orgIdx) {
        this.orgIdx = orgIdx;
    }

    public Product() {
    }

    public Product( String productName,Integer createBy,String orgIdx, Integer quantity, String createByName,String size) {
        this.productName = productName;
        this.createBy = createBy;
        this.createByName = createByName;
        this.orgIdx  = orgIdx;
        this.quantity = quantity;
        this.size = size;
    }

    public Integer getProductId() {
        return productId;
    }

    public Product setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Product setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getSize() {
        return size;
    }

    public Product setSize(String size) {
        this.size = size;
        return this;
    }
}

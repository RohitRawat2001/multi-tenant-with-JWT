package com.amran.dynamic.multitenant.mastertenant.service;

import com.amran.dynamic.multitenant.mastertenant.entity.MasterTenant;

import java.util.List;

public interface MasterTenantService {

    MasterTenant findByClientId(Integer clientId);
    List<Integer> getAllTenantClientIds();
    MasterTenant createTenant(MasterTenant tenant);
}

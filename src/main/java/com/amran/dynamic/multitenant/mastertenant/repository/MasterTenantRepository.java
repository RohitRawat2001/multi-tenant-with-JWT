package com.amran.dynamic.multitenant.mastertenant.repository;

import com.amran.dynamic.multitenant.mastertenant.entity.MasterTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MasterTenantRepository extends JpaRepository<MasterTenant, Integer> {
    MasterTenant findByTenantClientId(Integer clientId);
    @Query("SELECT m.tenantClientId FROM MasterTenant m")
    List<Integer> findAllTenantClientIds();
}

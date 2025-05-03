package com.amran.dynamic.multitenant.mastertenant;

import com.amran.dynamic.multitenant.mastertenant.entity.MasterTenant;
import com.amran.dynamic.multitenant.mastertenant.entity.OrgDTO;
import com.amran.dynamic.multitenant.mastertenant.service.MasterTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class controller {

    @Autowired
    private  MasterTenantService service;

    @GetMapping("/tenant-ids")
    public Map<String, List<Integer>> getOrgIdxList() {
        //List<Integer> ids = service.getAllTenantClientIds();

//        Map<Integer, String> orgNames = Map.of(
//                1, "Company 1",
//                2, "Company 2"
//        );
//
//        List<OrgDTO> result = ids.stream()
//                .map(id -> new OrgDTO(id, orgNames.getOrDefault(id, "Unknown Org")))
//                .toList();
//
//        return Map.of("tenants", result);
        List<Integer> ids = service.getAllTenantClientIds();
        return Map.of("tenantClientIds", ids);
    }


    @PostMapping("/organisation")
    public MasterTenant createTenant(@RequestBody MasterTenant tenant) {
        try {
            return service.createTenant(tenant);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

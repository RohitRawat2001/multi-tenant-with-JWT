package com.amran.dynamic.multitenant.mastertenant.entity;
public class OrgDTO {
    private Integer orgIdx;
    private String orgName;

    public OrgDTO(Integer orgIdx, String orgName) {
        this.orgIdx = orgIdx;
        this.orgName = orgName;
    }

    public Integer getOrgIdx() {
        return orgIdx;
    }

    public String getOrgName() {
        return orgName;
    }
}

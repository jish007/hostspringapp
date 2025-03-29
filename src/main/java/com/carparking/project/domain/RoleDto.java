package com.carparking.project.domain;

public class RoleDto {

    private String roleName;

    private String responsibilities;

    private String adminMailId;

    public RoleDto() {}

    public RoleDto(String roleName, String responsibilities) {
        this.roleName = roleName;
        this.responsibilities = responsibilities;
    }

    public String getAdminMailId() {
        return adminMailId;
    }

    public void setAdminMailId(String adminMailId) {
        this.adminMailId = adminMailId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }
}

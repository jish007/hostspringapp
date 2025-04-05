package com.carparking.project.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "roles_master")
public class Role {

    @Id
    @Column(name = "rolename")
    private String roleName;

    private String responsibilities;

    public Role() {
    }

    public Role(String roleName, String responsibilities) {
        this.roleName = roleName;
        this.responsibilities = responsibilities;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleName, role.roleName) && Objects.equals(responsibilities, role.responsibilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName, responsibilities);
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }
}
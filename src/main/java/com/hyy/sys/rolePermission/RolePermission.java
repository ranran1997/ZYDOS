package com.hyy.sys.rolePermission;

import com.hyy.base.AbstractItem;

import javax.persistence.*;

/**
 * Created by hengyanyan on 2017/3/7.
 */
@Entity
@Table(name = "zydos_role_permission", schema = "", catalog = "zydos")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class RolePermission extends AbstractItem {

    @Id
    @Column(name = "id", nullable = false, length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "role_id", nullable = false, length = 10)
    private Integer roleId;

    @Basic
    @Column(name = "permission_id", nullable = false, length = 10)
    private Integer permissionId;

    public RolePermission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}

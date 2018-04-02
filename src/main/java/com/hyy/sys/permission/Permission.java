package com.hyy.sys.permission;

import com.hyy.base.AbstractItem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/26  1:54
 */
@Entity
@Table(name = "zydos_permission")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class Permission extends AbstractItem implements Serializable {

    @Id
    @Column(name = "id", nullable = false, length = 8)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Basic
    @Column(name = "path", nullable = false, length = 32)
    private String path;

    @Basic
    @Column(name = "is_menu", nullable = false, length = 1)
    private Integer isMenu;

    @Basic
    @Column(name = "is_button", nullable = false, length = 1)
    private Integer isButton;

    @Basic
    @Column(name = "parent_id", nullable = false, length = 8)
    private Integer parentId;

    @Basic
    @Column(name = "order", nullable = false, length = 5)
    private Integer order;

    @Basic
    @Column(name = "icon", nullable = false, length = 64)
    private String icon;

    @Basic
    @Column(name = "model", nullable = true, length = 30)
    private String model;

    @Basic
    @Column(name = "status", nullable = true, length = 1)
    private Integer status;

    @Basic
    @Column(name = "desc", nullable = true, length = 30)
    private String desc;

    @Transient
    List<Permission> childPermissions;

    @Transient
    Integer selected = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getIsButton() {
        return isButton;
    }

    public void setIsButton(Integer isButton) {
        this.isButton = isButton;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Permission> getChildPermissions() {
        if (childPermissions == null)
            childPermissions = new ArrayList<Permission>();
        return childPermissions;
    }

    public void setChildPermissions(List<Permission> childPermissions) {
        this.childPermissions = childPermissions;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}

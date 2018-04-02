package com.hyy.sys.role;


import com.hyy.base.AbstractItem;
import com.hyy.sys.user.User;
import com.hyy.sys.rank.Rank;

import javax.persistence.*;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/26  1:08
 */
@Entity
@Table(name ="zydos_role")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class Role extends AbstractItem{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "parent_id")
    private Integer parentId;

    @Basic
    @Column(name = "desc")
    private String desc;

    @Transient
    private List<Role> childRole;

    public Role(){

    }

    public Role(Integer id,String name){
        this.id = id;
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Role> getChildRole() {
        return childRole;
    }

    public void setChildRole(List<Role> childRole) {
        this.childRole = childRole;
    }





}

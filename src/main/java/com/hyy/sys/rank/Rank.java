package com.hyy.sys.rank;

import com.hyy.base.AbstractItem;
import com.hyy.sys.role.Role;

import javax.persistence.*;

/**
 * 与权限表外键关联，执行删除时若权限表有匹配项，删除操作被拒绝
 * Created by HengYanYan on 2016/4/26  1:54
 */
@Entity
@Table(name = "zydos_rank")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class Rank extends AbstractItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rank")
    private String rank;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "parent_id")
    private Integer parentId;


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "desc")
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

package com.hyy.view.vdelivererdetail;

import com.hyy.base.AbstractItem;

import javax.persistence.*;
import java.sql.Date;

/**
 * 查询有关配送员所有角色等级信息
 * Created by HengYanYan on 2016/4/30  20:17
 */
@Entity
@Table(name = "v_deliverer_detail", schema = "", catalog = "zydos")
public class VDelivererDetail extends AbstractItem {
    private Integer id;
    private String jobNumber;
    private String name;
    private String mobile;
    private Integer storeNum;
    private String role;
    private Date joinTime;
    private Short sex;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "job_number")
    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "storeNum")
    public Integer getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(Integer storeNum) {
        this.storeNum = storeNum;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "join_time")
    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }


    @Basic
    @Column(name = "sex")
    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }
}

package com.hyy.zydos.customer;

import com.hyy.base.AbstractItem;

import javax.persistence.*;

/**
 * Created by HengYanYan on 2016/4/13  16:28
 */
@Entity
@Table(name = "zydos_customer", schema = "", catalog = "zydos")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class Customer extends AbstractItem {
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
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sex")
    private Byte sex;

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "mobile1")
    private String mobile1;

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    @Basic
    @Column(name = "mobile2")
    private String mobile2;

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    @Basic
    @Column(name = "address")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "describe")
    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Customer(){

    }
    public Customer(Integer id,String name){
        this.id = id;
        this.name = name;
    }
    public Customer(String name,String mobile1){
        super();
        this.name = name;
        this.mobile1 = mobile1;
    }
}

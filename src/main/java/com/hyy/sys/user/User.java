package com.hyy.sys.user;

import com.hyy.base.AbstractItem;
import com.hyy.sys.role.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/13  16:28
 */
@Entity
@Table(name = "zydos_user", schema = "", catalog = "zydos")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class User extends AbstractItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "sex")
    private short sex;

    @Basic
    @Column(name = "mobile")
    private String mobile;

    @Basic
    @Column(name = "image")
    private String image;

    @Basic
    @Column(name = "is_admin")
    private Integer isAdmin;

    @Basic
    @Column(name = "role_id")
    private Integer roleId;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @Basic
    @Column(name = "first_login")
    private short firstLogin;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "lng_lat")
    private String lngLat;

    @Transient
    private List<User> childUsers = new ArrayList<User>();

    @Transient
    private Integer storeCount;

    @Transient
    private List<Integer> childUserIds;

    @Transient
    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public short getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(short firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLngLat() {
        return lngLat;
    }

    public void setLngLat(String lngLat) {
        this.lngLat = lngLat;
    }

    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<User> getChildUsers() {
        return childUsers;
    }

    public void setChildUsers(List<User> childUsers) {
        this.childUsers = childUsers;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
    }

    public List<Integer> getChildUserIds() {
        return childUserIds;
    }

    public void setChildUserIds(List<Integer> childUserIds) {
        this.childUserIds = childUserIds;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /*fetch = FetchType.EAGER表急加载*/
   /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER , mappedBy = "delivererId")
    private List<Store> storesOfDeliverer;

    public List<Store> getStoresOfDeliverer() {
        return storesOfDeliverer;
    }

    public void setStoresOfDeliverer(List<Store> storesOfDeliverer) {
        this.storesOfDeliverer = storesOfDeliverer;
    }*/

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (id != that.id) return false;
        if (sex != that.sex) return false;
        if (isAdmin != that.isAdmin) return false;
        if (isDeliverer != that.isDeliverer) return false;
        if (firstLogin != that.firstLogin) return false;
        if (jobNumber != null ? !jobNumber.equals(that.jobNumber) : that.jobNumber != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (district != null ? !district.equals(that.district) : that.district != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (streetNumber != null ? !streetNumber.equals(that.streetNumber) : that.streetNumber != null) return false;
        if (lngLat != null ? !lngLat.equals(that.lngLat) : that.lngLat != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        return true;
    }*/

   /* @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (jobNumber != null ? jobNumber.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) sex;
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (streetNumber != null ? streetNumber.hashCode() : 0);
        result = 31 * result + (lngLat != null ? lngLat.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (int) isAdmin;
        result = 31 * result + (int) firstLogin;
        return result;
    }*/

}

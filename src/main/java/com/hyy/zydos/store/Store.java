package com.hyy.zydos.store;

import com.hyy.base.AbstractItem;

import javax.persistence.*;


/**
 * Created by HengYanYan on 2016/4/13  16:28
 */
@Entity
@Table(name = "zydos_store", schema = "", catalog = "zydos")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
/*dynamicUpdate = true, dynamicInsert = true，动态更新及插入，高效*/
public class Store extends AbstractItem {

    @Id
    @Column(name = "id")
    /*定义主键生成策略*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "deliverer_id")
    private Integer delivererId;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "store_phone")
    private String storePhone;

    @Column(name = "address")
    private String address;

    @Column(name = "longitude")
    private String lng;

    @Column(name = "latitude")
    private String lat;

    @Transient
    private String delivererName;

    @Transient
    private String ownerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(Integer delivererId) {
        this.delivererId = delivererId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getDelivererName() {
        return delivererName;
    }

    public void setDelivererName(String delivererName) {
        this.delivererName = delivererName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String owner_name) {
        this.ownerName = owner_name;
    }

    /*@OneToMany(mappedBy = "storeId")
    private Collection<Order> ordersOfStore;

    public Collection<Order> getOrdersOfStore() {
        return ordersOfStore;
    }

    public void setOrdersOfStore(Collection<Order> ordersOfStore) {
        this.ordersOfStore = ordersOfStore;
    }

*/
  /*  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Store that = (Store) o;

        if (id != that.id) return false;
        if (storeName != null ? !storeName.equals(that.storeName) : that.storeName != null) return false;
        if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null) return false;
        if (ownerName != null ? !ownerName.equals(that.ownerName) : that.ownerName != null) return false;
        if (ownerMobile != null ? !ownerMobile.equals(that.ownerMobile) : that.ownerMobile != null) return false;
        if (storePhone != null ? !storePhone.equals(that.storePhone) : that.storePhone != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (district != null ? !district.equals(that.district) : that.district != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (streetNumber != null ? !streetNumber.equals(that.streetNumber) : that.streetNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (storeName != null ? storeName.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (ownerName != null ? ownerName.hashCode() : 0);
        result = 31 * result + (ownerMobile != null ? ownerMobile.hashCode() : 0);
        result = 31 * result + (storePhone != null ? storePhone.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (streetNumber != null ? streetNumber.hashCode() : 0);
        return result;
    }*/



}

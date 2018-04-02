package com.hyy.view.vundoorderstore;

import com.hyy.base.AbstractItem;

import javax.persistence.*;

/**
 * Created by HengYanYan on 2016/5/1  14:00
 */
@Entity
@Table(name = "v_undo_order_store", schema = "", catalog = "zydos")
public class VUndoOrderStore extends AbstractItem {
    private Integer id;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer isUrgent;

    @Basic
    @Column(name = "is_urgent")
    public Integer getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Integer isUrgent) {
        this.isUrgent = isUrgent;
    }

    private  java.util.Date deadline;

    @Basic
    @Column(name = "deadline")
    public  java.util.Date getDeadline() {
        return deadline;
    }

    public void setDeadline( java.util.Date deadline) {
        this.deadline = deadline;
    }

    private String extraMsg;

    @Basic
    @Column(name = "extra_msg")
    public String getExtraMsg() {
        return extraMsg;
    }

    public void setExtraMsg(String extraMsg) {
        this.extraMsg = extraMsg;
    }

    private String storeName;

    @Basic
    @Column(name = "store_name")
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    private String clientName;

    @Basic
    @Column(name = "client_name")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    private String storePhone;

    @Basic
    @Column(name = "store_phone")
    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    private String address;

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String lng;

    @Basic
    @Column(name = "longitude")
    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    private String lat;

    @Basic
    @Column(name = "latitude")
    public String getLat() {
        return lat;
    }


    public void setLat(String lat) {
        this.lat = lat;
    }

    private Integer delivererId;

    @Basic
    @Column(name = "deliverer_id")
    public Integer getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(Integer delivererId) {
        this.delivererId = delivererId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VUndoOrderStore that = (VUndoOrderStore) o;

        if (id != that.id) return false;
        if (isUrgent != that.isUrgent) return false;
        if (delivererId != that.delivererId) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;
        if (extraMsg != null ? !extraMsg.equals(that.extraMsg) : that.extraMsg != null) return false;
        if (storeName != null ? !storeName.equals(that.storeName) : that.storeName != null) return false;
        if (clientName != null ? !clientName.equals(that.clientName) : that.clientName != null) return false;
        if (storePhone != null ? !storePhone.equals(that.storePhone) : that.storePhone != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (lng != null ? !lng.equals(that.lng) : that.lng != null) return false;
        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) isUrgent;
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (extraMsg != null ? extraMsg.hashCode() : 0);
        result = 31 * result + (storeName != null ? storeName.hashCode() : 0);
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (storePhone != null ? storePhone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (int) delivererId;
        return result;
    }
}

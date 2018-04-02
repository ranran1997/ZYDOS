package com.hyy.zydos.orderInfo;

import com.hyy.base.AbstractItem;
import com.hyy.zydos.store.Store;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by HengYanYan on 2016/4/13  16:28
 */
@Entity
@Table(name = "zydos_order_info", schema = "", catalog = "zydos")
public class OrderInfo extends AbstractItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "store_id")
    private Integer storeId;

    @Basic
    @Column(name = "store_name")
    private String storeName;

    @Basic
    @Column(name = "deliverer_id")
    private Integer delivererId;

    @Basic
    @Column(name = "deliverer_name")
    private String delivererName;

    @Basic
    @Column(name = "is_urgent")
    private Integer isUrgent;

    @Basic
    @Column(name = "creater")
    private String creater;

    @Basic
    @Column(name = "extra_msg")
    private String extraMsg;

    @Basic
    @Column(name = "add_time")
    private Date addTime;

    @Basic
    @Column(name = "deadline")
    private Date deadline;

    @Basic
    @Column(name = "deliver_time")
    private Date deliverTime;

    @Basic
    @Column(name = "order_status")
    private Byte orderStatus;

    @Basic
    @Column(name = "pay_status")
    private Integer payStatus;

    @Transient
    private Store store;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(Integer delivererId) {
        this.delivererId = delivererId;
    }

    public Integer getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Integer isUrgent) {
        this.isUrgent = isUrgent;
    }

    public String getExtraMsg() {
        return extraMsg;
    }

    public void setExtraMsg(String extraMsg) {
        this.extraMsg = extraMsg;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDelivererName() {
        return delivererName;
    }

    public void setDelivererName(String delivererName) {
        this.delivererName = delivererName;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order that = (Order) o;

        if (id != that.id) return false;
        if (addTime != that.addTime) return false;
        if (orderStatus != that.orderStatus) return false;
        if (deliverTime != null ? !deliverTime.equals(that.deliverTime) : that.deliverTime != null) return false;
        if (payStatus != null ? !payStatus.equals(that.payStatus) : that.payStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + addTime;
        result = 31 * result + (int) orderStatus;
        result = 31 * result + (deliverTime != null ? deliverTime.hashCode() : 0);
        result = 31 * result + (payStatus != null ? payStatus.hashCode() : 0);
        return result;
    }*/

/*
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id", nullable = false)
    public Store getStoreId() {
        return storeId;
    }

    public void setStoreId(Store storeId) {
        this.storeId = storeId;
    }
*/

}

package com.hyy.zydos.customerStore;

import com.hyy.base.AbstractItem;

import javax.persistence.*;

/**
 * Created by hengyanyan on 2017/3/13.
 */
@Entity
@Table(name = "zydos_customer_store", schema = "", catalog = "zydos")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class CustomerStore extends AbstractItem {

    @Id
    @Column(name = "id", nullable = false, length = 8)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "customer_id", nullable = false, length = 8)
    private Integer customerId;

    @Basic
    @Column(name = "store_id", nullable = false, length = 8)
    private Integer storeId;

    public CustomerStore(Integer customerId, Integer storeId) {
        this.customerId = customerId;
        this.storeId = storeId;
    }

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}

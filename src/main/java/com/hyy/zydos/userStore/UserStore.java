package com.hyy.zydos.userStore;

import com.hyy.base.AbstractItem;

import javax.persistence.*;

/**
 * Created by hengyanyan on 2017/3/13.
 */
@Entity
@Table(name = "zydos_customer_store", schema = "", catalog = "zydos")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class UserStore extends AbstractItem {

    @Id
    @Column(name = "id", nullable = false, length = 8)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "user_id", nullable = false, length = 8)
    private String userId;

    @Basic
    @Column(name = "store_id", nullable = false, length = 8)
    private String storeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}

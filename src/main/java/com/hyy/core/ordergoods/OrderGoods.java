package com.hyy.core.ordergoods;

import javax.persistence.*;

/**
 * Created by HengYanYan on 2016/4/13  16:28
 */
@Entity
@Table(name = "zydos_order_goods", schema = "", catalog = "zydos")
public class OrderGoods {
    private int id;
    private int orderId;
    private int goodsId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "goods_id")
    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderGoods that = (OrderGoods) o;

        if (id != that.id) return false;
        if (orderId != that.orderId) return false;
        if (goodsId != that.goodsId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + orderId;
        result = 31 * result + goodsId;
        return result;
    }
}

package com.hyy.view.vstoredelivererclient;

import com.hyy.base.AbstractItem;

import javax.persistence.*;

/**
 * Created by HengYanYan on 2016/4/30  15:55
 */
@Entity
@Table(name = "v_store_deliverer_client", schema = "", catalog = "zydos")
public class VStoreDelivererClient extends AbstractItem {
    private String delivererName;
    private Integer id;
    private String clientName;

    @Basic
    @Column(name = "deliverer_name")
    public String getDelivererName() {
        return delivererName;
    }

    public void setDelivererName(String delivererName) {
        this.delivererName = delivererName;
    }

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "client_name")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VStoreDelivererClient that = (VStoreDelivererClient) o;

        if (id != that.id) return false;
        if (delivererName != null ? !delivererName.equals(that.delivererName) : that.delivererName != null)
            return false;
        if (clientName != null ? !clientName.equals(that.clientName) : that.clientName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = delivererName != null ? delivererName.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        return result;
    }
}

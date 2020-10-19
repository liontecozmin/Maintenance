package com.tfo.maintenance.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = ".dbo.TrackingItem")
@Data
@Getter
public class VesselStatus {

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(Date alertDate) {
        this.alertDate = alertDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private String id;
    @Column(name = "IsActive")
    private Integer status;
    @Column(name = "TenantId")
    private String tenantId;
    //    @Column(name = "IMO")
//    private String imo;
//    @Column(name = "VesselName")
//    private String vesselName;
    //TODO join with vesselTracking table for IMO
    @Column(name = "CreatedAt")
    private Date alertDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}

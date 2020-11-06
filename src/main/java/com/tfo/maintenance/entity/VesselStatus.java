package com.tfo.maintenance.entity;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvRecurse;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.sql.Date;

@Entity
@Table(name = ".dbo.TrackingItem")
@Data
@Getter
public class VesselStatus {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
//
//    public Date getAlertDate() {
//        return alertDate;
//    }
//
//    public void setAlertDate(Date alertDate) {
//        this.alertDate = alertDate;
//    }

//    @OneToOne(cascade = CascadeType.ALL)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    @CsvBindByName(column ="ID")
    private String id;
    @Column(name = "IsActive")
    @CsvBindByName(column ="STATUS")
    private Integer status;
    @CsvIgnore
    @Column(name = "TenantId")
    private String tenantId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id", referencedColumnName = "trackingItemId")
    @CsvRecurse
    VesselLink vesselLink;
    //TODO join with vesselTracking table for IMO
    @Column(name = "CreatedAt")
    @CsvIgnore
    private Date alertDate;
    @Column(name = "LastScreeningType")
    @CsvIgnore
    private Integer screeningType;




//    public String getTenantId() {
//        return tenantId;
//    }
//
//    public void setTenantId(String tenantId) {
//        this.tenantId = tenantId;
//    }

    @Override
    public String toString() {
        return "VesselStatus{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", tenantId='" + tenantId + '\'' +
                ", vesselLink=" + vesselLink +
                ", alertDate=" + alertDate +
                '}';
    }
}

package com.tfo.maintenance.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

//Linked with vesselStatus for getting vessel imo and flag
@Entity
@Table(name = ".dbo.VesselTrackingItem")
@Data
@Getter
public class VesselLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trackingItemId")
    @CsvIgnore
    private String trackingItemId;
    @Column(name = "name")
    @CsvBindByName(column ="VESSELNAME")
    private String vesselName;

    @Override
    public String toString() {
        return "VesselLink{" +
//                ", vesselName='" + vesselName + '\'' +
//                ", vesselFlag='" + vesselFlag + '\'' +
//                ", imo='" + imo + '\'' +
                '}';
    }

//    public String getTrackingItemId() {
//        return trackingItemId;
//    }
//
//    public void setTrackingItemId(String trackingItemId) {
//        this.trackingItemId = trackingItemId;
//    }
//
    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVesselFlag() {
        return vesselFlag;
    }

    public void setVesselFlag(String vesselFlag) {
        this.vesselFlag = vesselFlag;
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }
//
//    public VesselStatus getVesselStatus() {
//        return vesselStatus;
//    }
//
//    public void setVesselStatus(VesselStatus vesselStatus) {
//        this.vesselStatus = vesselStatus;
//    }

    @Column(name = "flag")
    @CsvBindByName(column ="VESSELFLAG")
    private String vesselFlag;
    @Column(name = "Imo")
    @CsvBindByName(column ="IMO")
    private String imo;
    @OneToOne(mappedBy = "vesselLink")
    private VesselStatus vesselStatus;


}

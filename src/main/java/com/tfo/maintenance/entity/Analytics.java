package com.tfo.maintenance.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

//Linked with vesselStatus for getting vessel imo and flag
@Entity
@Table(name = ".dbo.AnalyticsEntries")
@Data
@Getter
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private String id;
    @Column(name = "TenantCode")
    private String tenant;
//    @Column(name = "BranchId")
//    private String branchId;
    @Column(name = "EventId")
    private String eventId;
//    @Column(name = "StringValue")
//    private String stringValue;
    @Column(name = "Timestamp")
    private Date timestamp;
    @Column(name = "OcrInProgress")
    private String ocrInProgress;
    @Column(name = "OcrCompleted")
    private String ocrCompleted;
    @Column(name = "RefinementPending")
    private String refinementPending;
    @Column(name = "RefinementInProgress")
    private String refinementInProgress;
    @Column(name = "RefinementCompleted")
    private String refinementCompleted;
    @Column(name = "EvaluationPending")
    private String evaluationPending;
    @Column(name = "CompletedApproved")
    private String completedApproved;
    @Column(name = "CompletedRejected")
    private String completedRejected;
//
//
//    @Column(name = "flag")
//    @CsvBindByName(column ="VESSELFLAG")
//    private String vesselFlag;
//    @Column(name = "Imo")
//    @CsvBindByName(column ="IMO")
//    private String imo;
//    @OneToOne(mappedBy = "vesselLink")
//    private VesselStatus vesselStatus;
    //TODO join with vesselTracking table for IMO

}

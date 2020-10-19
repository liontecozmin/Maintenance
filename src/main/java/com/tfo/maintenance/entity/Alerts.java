package com.tfo.maintenance.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = ".vesselmonitor.Alert")
@Data
public class Alerts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AlertId")
    private String alertId;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "AlertType")
    private String alertType;
    @Column(name = "AlertDate")
    private Date alertDate;
    @Column(name = "WorkItemId")
    private String workItemId;
    @Column(name = "TenantId")
    private String tenantId;
    @Column(name = "BranchId")
    private String branchId;
    @Column(name = "EventId")
    private String eventId;
    @Column(name = "RuleId")
    private String ruleId;
    @Column(name = "Description")
    private String description;
    @Column(name = "IMO")
    private String imo;
    @Column(name = "VesselName")
    private String vesselName;
    @Column(name = "VesselFlag")
    private String vesselFlag;
    @Column(name = "Owner")
    private String owner;
    @Column(name = "BeneficiaryOwner")
    private String beneficiaryOwner;
    @Column(name = "VoyageNr")
    private String voyageNr;
    @Column(name = "DeparturePort")
    private String departurePort;
    @Column(name = "DepartureDate")
    private Date departureDate;
    @Column(name = "ArrivalPort")
    private String arrivalPort;
    @Column(name = "ArrivalDate")
    private Date arrivalDate;
    @Column(name = "BrokenRules")
    private String brokenRules;
    @Column(name = "InsertDateTime")
    private Date insertDateTime;
    @Column(name = "UpdateDateTime")
    private Date updateDateTime;

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public Date getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(Date alertDate) {
        this.alertDate = alertDate;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBeneficiaryOwner() {
        return beneficiaryOwner;
    }

    public void setBeneficiaryOwner(String beneficiaryOwner) {
        this.beneficiaryOwner = beneficiaryOwner;
    }

    public String getVoyageNr() {
        return voyageNr;
    }

    public void setVoyageNr(String voyageNr) {
        this.voyageNr = voyageNr;
    }

    public String getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(String departurePort) {
        this.departurePort = departurePort;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalPort() {
        return arrivalPort;
    }

    public void setArrivalPort(String arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getBrokenRules() {
        return brokenRules;
    }

    public void setBrokenRules(String brokenRules) {
        this.brokenRules = brokenRules;
    }

    public Date getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(Date insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}

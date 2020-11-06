package com.tfo.maintenance.dao.analytics;

import com.tfo.maintenance.entity.Alerts;
import com.tfo.maintenance.entity.Analytics;
import com.tfo.maintenance.entity.VesselLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.rmi.server.UID;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public interface AnalyticsRepository extends JpaRepository<Analytics, UID> {

    @Query("SELECT a FROM Analytics a WHERE a.id like ?1")
    Optional<Analytics> findById(String id);

    @Query(value = "SELECT \n" +
            "       ae.Id,\n" +
            "       ae.EventId,\n" +
            "       ae.TenantCode,\n" +
            "       ae.Timestamp,\n" +
            "       (SELECT TOP 1 [Name] FROM [Conpend.Database.Account].dbo.[Tenants] t WHERE t.Code = ae.TenantCode) as 'Tenant name',\n" +
            "       (SELECT TOP 1 [StringValue] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' order by ID DESC) as 'Current status',\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'OcrInProgress' order by ID) as OcrInProgress,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'OcrCompleted' order by ID DESC) as OcrCompleted,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'RefinementPending' order by ID) as RefinementPending,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'RefinementInProgress' order by ID) as RefinementInProgress,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'RefinementCompleted' order by ID DESC) as RefinementCompleted,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'EvaluationPending' order by ID) as EvaluationPending,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'CompletedApproved' order by ID) as CompletedApproved,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'CompletedRejected' order by ID DESC) as CompletedRejected\n" +
            "FROM \n" +
            "       [Conpend.Database.Analytics].dbo.AnalyticsEntries ae \n" +
            "WHERE\n" +
            "       [Key] = 'Status' AND StringValue in ('OcrInProgress', 'OcrCompleted', 'RefinementPending', 'RefinementInProgress', 'RefinementCompleted', 'EvaluationPending', 'CompletedApproved', 'CompletedRejected')\n" +
            "AND \n ae.Timestamp BETWEEN ?1 AND ?2 \n" +
            "GROUP BY \n" +
            "       ae.EventId, ae.Id, ae.Timestamp, ae.TenantCode\n", nativeQuery = true)
    Collection<Analytics> getKpi0(Date startDate, Date endDate);

    @Query(value = "SELECT \n" +
            "       ae.Id,\n" +
            "       ae.EventId,\n" +
            "       ae.TenantCode,\n" +
            "       ae.Timestamp,\n" +
            "       (SELECT TOP 1 [Name] FROM [Conpend.Database.Account].dbo.[Tenants] t WHERE t.Code = ae.TenantCode) as 'Tenant name',\n" +
            "       (SELECT TOP 1 [StringValue] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' order by ID DESC) as 'Current status',\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'OcrInProgress' order by ID) as OcrInProgress,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'OcrCompleted' order by ID DESC) as OcrCompleted,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'RefinementPending' order by ID) as RefinementPending,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'RefinementInProgress' order by ID) as RefinementInProgress,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'RefinementCompleted' order by ID DESC) as RefinementCompleted,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'EvaluationPending' order by ID) as EvaluationPending,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'CompletedApproved' order by ID) as CompletedApproved,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'CompletedRejected' order by ID DESC) as CompletedRejected\n" +
            "FROM \n" +
            "       [Conpend.Database.Analytics].dbo.AnalyticsEntries ae \n" +
            "WHERE\n" +
            "       [Key] = 'Status' AND StringValue in ('OcrInProgress', 'OcrCompleted', 'RefinementPending', 'RefinementInProgress', 'RefinementCompleted', 'EvaluationPending', 'CompletedApproved', 'CompletedRejected')\n" +
            "AND \n ae.Timestamp BETWEEN ?2 AND ?3 \n" +
            "AND \n ae.TenantCode like ?1 \n" +
            "GROUP BY \n" +
            "       ae.EventId, ae.Id, ae.Timestamp, ae.TenantCode\n", nativeQuery = true)
    Collection<Analytics> getKpi1(String tenantId, Date startDate, Date endDate);

    @Query(value = "SELECT \n" +
            "       ae.Id,\n" +
            "       ae.EventId,\n" +
            "       ae.TenantCode,\n" +
            "       ae.Timestamp,\n" +
            "       (SELECT TOP 1 [Name] FROM [Conpend.Database.Account].dbo.[Tenants] t WHERE t.Code = ae.TenantCode) as 'Tenant name',\n" +
            "       (SELECT TOP 1 [StringValue] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' order by ID DESC) as 'Current status',\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'OcrInProgress' order by ID) as OcrInProgress,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'OcrCompleted' order by ID DESC) as OcrCompleted,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'RefinementPending' order by ID) as RefinementPending,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'RefinementInProgress' order by ID) as RefinementInProgress,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'RefinementCompleted' order by ID DESC) as RefinementCompleted,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'EvaluationPending' order by ID) as EvaluationPending,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'CompletedApproved' order by ID) as CompletedApproved,\n" +
            "       (SELECT TOP 1 [Timestamp] FROM [Conpend.Database.Analytics].dbo.AnalyticsEntries WHERE EventId = ae.EventId AND [Key] = 'Status' AND StringValue = 'CompletedRejected' order by ID DESC) as CompletedRejected\n" +
            "FROM \n" +
            "       [Conpend.Database.Analytics].dbo.AnalyticsEntries ae \n" +
            "WHERE\n" +
            "       [Key] = 'Status' AND StringValue in ('OcrInProgress', 'OcrCompleted', 'RefinementPending', 'RefinementInProgress', 'RefinementCompleted', 'EvaluationPending', 'CompletedApproved', 'CompletedRejected')\n" +
            "AND \n ae.Timestamp BETWEEN ?3 AND ?4 \n" +
            "AND \n (ae.TenantCode like ?1 OR ae.TenantCode like ?2 \n" +
            "GROUP BY \n" +
            "       ae.EventId, ae.Id, ae.Timestamp, ae.TenantCode\n", nativeQuery = true)
    Collection<Analytics> getKpi2(String tenantId1, String tenantId2, Date startDate, Date endDate);


}

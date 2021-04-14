package com.tfo.maintenance.dao.alerts;

import com.tfo.maintenance.entity.Alerts;
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
public interface AlertsRepository extends JpaRepository<Alerts, UID> {

    @Query("SELECT a FROM Alerts a WHERE a.alertId like ?1")
    Optional<Alerts> findById(String alertId);

    @Query("SELECT a FROM Alerts a WHERE DAY(a.alertDate)=4 AND a.alertDate BETWEEN ?1 AND ?2 order by a.alertDate DESC")
    Collection<Alerts> getReport0(Date startDate, Date endDate);

    @Query("SELECT a FROM Alerts a WHERE a.tenantId like %?1 AND DAY(a.alertDate)=4 AND a.alertDate BETWEEN ?2 AND ?3 order by a.alertDate DESC")
    Collection<Alerts> getReport1(String tenantId, Date startDate, Date endDate);

    @Query("SELECT a FROM Alerts a WHERE (a.tenantId like %?1 OR a.tenantId like %?2) AND DAY(a.alertDate)=4 AND a.alertDate BETWEEN ?3 AND ?4 order by a.alertDate DESC")
    Collection<Alerts> getReport2(String tenantId1, String tenantId2, Date startDate, Date endDate);

    @Query(value = "SELECT  DISTINCT b.AlertId,\n" +
            "        t.CorrelationId,\n" +
            "        (b.imo),\n" +
            "        [CreatedAt],\n" +
            "        [TrackingItemId],\n" +
            "        b.Description,\n" +
            "        t.BranchId,\n" +
            "        REPLACE(t.TenantId,'00000000-0000-0000-0000-000000000001', 'Germany') as TenantId,\n" +
            "    t.EventId\n" +
            "\t\t--,m.TfoBranchName\n" +
            "\t\t,v.Name\n" +
            "\t\t,v.Imo\n" +
            "\t\t,v.DeparturePort\n" +
            "\t\t,v.DepartureDate\n" +
            "\t\t,v.ArrivalPort\n" +:wq

            "\t\t,v.ArrivalDate\n" +
            "\t\t,v.VoyageNumber\n" +
            "\t\t,v.Owner\n" +
            "\t\t,v.BeneficialOwner\n" +
            "\t\t,v.Flag\n" +
            "\t\t,v.LastMovementDate\n" +
            "\t\t,b.BrokenRules\n" +
            "\n" +
            "    FROM [Conpend.Database.Core].[dbo].[VesselTrackingItem] AS v\n" +
            "    WITH (NOLOCK)\n" +
            "    inner JOIN [Conpend.Database.Core].[dbo].[TrackingItem] AS t\n" +
            "    on v.TrackingItemId=t.Id\n" +
            "    inner join  [Microsoft.Integration.Trafinas].[vesselmonitor].[Alert] as b\n" +
            "    on v.Imo=b.IMO and v.TrackingItemId=t.Id\n" +
            "    where CorrelationId is not null\n" +
            "    order by AlertId\"")
    Collection<Alerts> getBrokenRuleReport(String tenantId1, String tenantId2, Date startDate, Date endDate);

}

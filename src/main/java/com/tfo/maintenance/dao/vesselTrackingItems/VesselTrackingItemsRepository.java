package com.tfo.maintenance.dao.vesselTrackingItems;

import com.tfo.maintenance.entity.Alerts;
import com.tfo.maintenance.entity.VesselStatus;
import com.tfo.maintenance.entity.VesselTrackingItem;
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
public interface VesselTrackingItemsRepository extends JpaRepository<VesselTrackingItem, UID> {
    @Query("SELECT a FROM VesselTrackingItem a WHERE a.id like ?1")
    Optional<VesselTrackingItem> findById(String id);

    @Query(value = "SELECT  DISTINCT b.AlertId,\n" +
            "        t.CorrelationId,\n" +
            "        b.imo,\n" +
            "        CreatedAt,\n" +
            "        TrackingItemId,\n" +
            "        b.Description,\n" +
            "        t.BranchId,\n" +
            "        REPLACE(t.TenantId,'00000000-0000-0000-0000-000000000001', 'Germany') as TenantId,\n" +
            "    t.EventId\n" +
            ",v.Name\n" +
            ",v.Imo\n" +
            ",v.DeparturePort\n" +
            ",v.DepartureDate\n" +
            ",v.ArrivalPort\n" +
            ",v.ArrivalDate\n" +
            ",v.VoyageNumber\n" +
            ",v.Owner\n" +
            ",v.BeneficialOwner\n" +
            ",v.Flag\n" +
            ",v.LastMovementDate\n" +
            ",b.BrokenRules\n" +
            "    FROM [Conpend.Database.Core].dbo.VesselTrackingItem AS v\n" +
            "    WITH (NOLOCK)\n" +
            "    inner JOIN [Conpend.Database.Core].dbo.TrackingItem AS t\n" +
            "    on v.TrackingItemId=t.Id\n" +
            "    inner join  [Microsoft.Integration.Trafinas].vesselmonitor.Alert as b\n" +
            "    on v.Imo=b.IMO and v.TrackingItemId=t.Id\n" +
            "    where CorrelationId is not null\n" +
            "    order by AlertId")
    Collection<Alerts> getBrokenRuleReport(String tenantId1, String tenantId2, Date startDate, Date endDate);
}

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
}

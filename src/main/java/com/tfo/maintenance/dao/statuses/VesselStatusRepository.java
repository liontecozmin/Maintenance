package com.tfo.maintenance.dao.statuses;

import com.tfo.maintenance.entity.Alerts;
import com.tfo.maintenance.entity.VesselStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.rmi.server.UID;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static com.ibm.java.diagnostics.utils.Context.logger;

@CrossOrigin
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public interface VesselStatusRepository extends JpaRepository<VesselStatus, UID> {

    @Query("SELECT a FROM VesselStatus a WHERE a.id like ?1")
    Optional<VesselStatus> findById(String id);

    @Query("SELECT a FROM VesselStatus a WHERE a.status =1")
    Optional<VesselStatus> findActive0(String id);
    @Query("SELECT a FROM VesselStatus a WHERE a.status =1  AND a.alertDate BETWEEN ?1 AND ?2 order by a.alertDate DESC")
    Collection<VesselStatus> findActive1(Date startDate, Date endDate);
    @Query("SELECT a FROM VesselStatus a WHERE a.status =1 AND a.tenantId like %?1 AND a.alertDate BETWEEN ?2 AND ?3 order by a.alertDate DESC")
    Collection<VesselStatus> findActive2(String tenantId, Date startDate, Date endDate);
    @Query("SELECT a FROM VesselStatus a WHERE a.status =1 AND a.tenantId like %?2 AND a.alertDate BETWEEN ?3 AND ?4 order by a.alertDate DESC")
    Collection<VesselStatus> findActive3(String tenantId1, String tenantId2, Date startDate, Date endDate);

    @Query("SELECT a FROM VesselStatus a WHERE a.status =0")
    Optional<VesselStatus> findInactive0(String id);
    @Query("SELECT a FROM VesselStatus a WHERE a.status =0  AND a.alertDate BETWEEN ?1 AND ?2 order by a.alertDate DESC")
    Collection<VesselStatus> findInactive1(Date startDate, Date endDate);
    @Query("SELECT a FROM VesselStatus a WHERE a.status =0 AND a.tenantId like %?1 AND a.alertDate BETWEEN ?2 AND ?3 order by a.alertDate DESC")
    Collection<VesselStatus> findInactive2(String tenantId, Date startDate, Date endDate);
    @Query("SELECT a FROM VesselStatus a WHERE a.status =0 AND a.tenantId like %?2 AND a.alertDate BETWEEN ?3 AND ?4 order by a.alertDate DESC")
    Collection<VesselStatus> findInactive3(String tenantId1, String tenantId2, Date startDate, Date endDate);

    @Query("SELECT a FROM VesselStatus a WHERE a.status =0 AND a.screeningType = 10 ")
    Optional<VesselStatus> findDeactivated0();
    @Query("SELECT a FROM VesselStatus a WHERE a.status =0 AND a.screeningType = 10 AND a.alertDate BETWEEN ?1 AND ?2 order by a.alertDate DESC")
    Collection<VesselStatus> findDeactivated1(Date startDate, Date endDate);
    @Query("SELECT a FROM VesselStatus a WHERE a.status =0 AND a.screeningType = 10 AND a.tenantId like %?1 AND a.alertDate BETWEEN ?2 AND ?3 order by a.alertDate DESC")
    Collection<VesselStatus> findDeactivated2(String tenantId, Date startDate, Date endDate);
    @Query("SELECT a FROM VesselStatus a WHERE a.status =0 AND a.screeningType = 10 AND (a.tenantId like %?1 OR a.tenantId like %?2) AND a.alertDate BETWEEN ?3 AND ?4 order by a.alertDate DESC")
    Collection<VesselStatus> findDeactivated3(String tenantId1, String tenantId2, Date startDate, Date endDate);
}

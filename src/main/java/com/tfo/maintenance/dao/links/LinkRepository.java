package com.tfo.maintenance.dao.links;

import com.tfo.maintenance.entity.VesselLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.rmi.server.UID;
import java.util.Optional;

@CrossOrigin
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public interface LinkRepository extends JpaRepository<VesselLink, UID> {

    @Query("SELECT a FROM VesselLink a WHERE a.trackingItemId like ?1")
    Optional<VesselLink> findById(String id);


}

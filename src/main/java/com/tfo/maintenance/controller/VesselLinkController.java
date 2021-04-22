package com.tfo.maintenance.controller;

import com.tfo.maintenance.dao.links.LinkRepository;
import com.tfo.maintenance.dao.vesselTrackingItems.VesselTrackingItemsRepository;
import com.tfo.maintenance.entity.Alerts;
import com.tfo.maintenance.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class VesselLinkController {

    private static final Logger logger = LoggerFactory.getLogger(VesselLinkController.class);
    @Autowired
    private LinkRepository vesselLinkRepository;

    @GetMapping("/brokenRule")
    public ResponseEntity getBrokenRule(@RequestParam(value = "tenantId1", required = false) String tenantId1, @RequestParam(value = "tenantId2", required = false) String tenantId2, @RequestParam(value = "dateStart") String startDate, @RequestParam(value = "dateEnd") String endDate, HttpServletResponse response)
            throws ResourceNotFoundException {
        logger.info("started csv BR api");
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        logger.info("date set");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=brokenRules" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        logger.info("header set");
        logger.info(startDate);
        logger.info(endDate);
        Collection<Alerts> brokenRules = vesselLinkRepository
                .getBrokenRuleReport(tenantId1, tenantId2, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
        brokenRules.stream().forEach(brokenRule -> logger.info("found brokenRule : {} {}", brokenRule.getImo(), brokenRule.getAlertDate()));

        logger.info("alerts data retrieved");
        return ResponseEntity.ok().body(brokenRules);

    }
}

package com.tfo.maintenance.controller;

import com.tfo.maintenance.dao.alerts.AlertsRepository;
import com.tfo.maintenance.entity.Alerts;
import com.tfo.maintenance.excel.ExcelFormatter;
import com.tfo.maintenance.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AlertsController {

    @Autowired
    private AlertsRepository alertsRepository;
    private static final Logger logger = LoggerFactory.getLogger(AlertsController.class);

    /**
     * Get all users list.
     *
     * @return the list
     */
    @GetMapping("/alerts")
    public List<Alerts> getAllAlerts() {
        return alertsRepository.findAll();
    }

    /**
     * Gets alerts by id.
     *
     * @param alertId the user id
     * @return the alerts by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/alerts/{id}")
    public ResponseEntity<Alerts> getUsersById(@PathVariable(value = "id") String alertId)
            throws ResourceNotFoundException {
        Alerts alert =
                alertsRepository
                        .findById(alertId)
                        .orElseThrow(() -> new ResourceNotFoundException("Alert not found on :: " + alertId));
        return ResponseEntity.ok().body(alert);
    }

    /**
     * Gets alerts reports.
     *
     * @param startDate
     * @param endDate
     * @return all the alerts
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/report/{startDate}/{endDate}")
    public ResponseEntity<Collection<Alerts>> getReport0(@PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate)
            throws ResourceNotFoundException {
        logger.info("started report api");
        Collection<Alerts> alert =
                alertsRepository
                        .getReport0(DateUtils.toDate(startDate), DateUtils.toDate(endDate));

        return ResponseEntity.ok().body(alert);
    }

    /**
     * Gets alerts report by country.
     *
     * @param tenantId the country id
     * @return the alerts by country
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/report/{tenantId}/{startDate}/{endDate}")
    public ResponseEntity<Collection<Alerts>> getReport1(@PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate)
            throws ResourceNotFoundException {
        logger.info("started report api");
        Collection<Alerts> alert =
                alertsRepository
                        .getReport1(tenantId, DateUtils.toDate(startDate), DateUtils.toDate(endDate));

        return ResponseEntity.ok().body(alert);
    }

    /**
     * Gets alerts report by 2 countries.
     *
     * @param tenantId1 the first country id
     * @param tenantId2 the second country id
     * @return the alerts by country
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/report/{tenantId1}/{tenantId2}/{startDate}/{endDate}")
    public ResponseEntity<Collection<Alerts>> getReport2(@PathVariable(value = "tenantId") String tenantId1, @PathVariable(value = "tenantId") String tenantId2, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate)
            throws ResourceNotFoundException {
        logger.info("started report api");
        Collection<Alerts> alert =
                alertsRepository
                        .getReport2(tenantId1, tenantId2, DateUtils.toDate(startDate), DateUtils.toDate(endDate));


        return ResponseEntity.ok().body(alert);
    }

    @GetMapping("/xlsreport/{startDate}/{endDate}")
    public ResponseEntity<Collection<Alerts>> getXlsReport(@PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response)
            throws ResourceNotFoundException {
        logger.info("started xls api");
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        logger.info("date set");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=alerts" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        logger.info("header set");
        Collection<Alerts> alertsList = alertsRepository
                .getReport0(DateUtils.toDate(startDate), DateUtils.toDate(endDate));

        alertsList.stream().forEach(alert -> logger.info("found alert : {} {}", alert.getImo(), alert.getAlertDate()));

        logger.info("alerts data retrieved");
        ExcelFormatter excelExporter = new ExcelFormatter(alertsList);
        try {
            excelExporter.export(response);
            logger.info("excel ready");
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ResourceNotFoundException("Xls could not be generated ");
    }

    @GetMapping("/xlsreport/{tenantId}/{startDate}/{endDate}")
    public ResponseEntity<Collection<Alerts>> getXlsReport(@PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response)
            throws ResourceNotFoundException {
        logger.info("started xls api");
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        logger.info("date set");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=alerts" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        logger.info("header set");
        Collection<Alerts> alertsList = alertsRepository
                .getReport1(tenantId, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
        alertsList.stream().forEach(alert -> logger.info("found alert : {} {}", alert.getImo(), alert.getAlertDate()));

        logger.info("alerts data retrieved");
        ExcelFormatter excelExporter = new ExcelFormatter(alertsList);
        try {
            excelExporter.export(response);
            logger.info("excel ready");
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ResourceNotFoundException("Xls could not be generated for ::" + tenantId);
    }

    @GetMapping("/xlsreport/{tenantId1}/{tenantId2}/{startDate}/{endDate}")
    public ResponseEntity getXlsReport(@PathVariable(value = "tenantId1") String tenantId1, @PathVariable(value = "tenantId2") String tenantId2, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response)
            throws ResourceNotFoundException {
        logger.info("started xls api");
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        logger.info("date set");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=alerts" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        logger.info("header set");
        Collection<Alerts> alertsList = alertsRepository
                .getReport2(tenantId1, tenantId2, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
        alertsList.stream().forEach(alert -> logger.info("found alert : {} {}", alert.getImo(), alert.getAlertDate()));

        logger.info("alerts data retrieved");
        ExcelFormatter excelExporter = new ExcelFormatter(alertsList);
        try {
            excelExporter.export(response);
            logger.info("excel ready");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("Xls could not be generated for ::" + tenantId1 + tenantId2);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/csvreport")
    public ResponseEntity getCsvReport(@RequestParam(value = "tenantId1", required = false) String tenantId1, @RequestParam(value = "tenantId2", required = false) String tenantId2, @RequestParam(value = "dateStart") String startDate, @RequestParam(value = "dateEnd") String endDate, HttpServletResponse response)
            throws ResourceNotFoundException {
        logger.info("started csv QP api");
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        logger.info("date set");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=alerts" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        logger.info("header set");
        logger.info(startDate);
        logger.info(endDate);
        Collection<Alerts> alertsList = alertsRepository
                .getReport2(tenantId1, tenantId2, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
        alertsList.stream().forEach(alert -> logger.info("found alert : {} {}", alert.getImo(), alert.getAlertDate()));

        logger.info("alerts data retrieved");
        return ResponseEntity.ok().body(alertsList);

    }



}



package com.tfo.maintenance.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.tfo.maintenance.dao.alerts.AlertsRepository;
import com.tfo.maintenance.dao.statuses.VesselStatusRepository;
import com.tfo.maintenance.entity.Alerts;
import com.tfo.maintenance.entity.VesselLink;
import com.tfo.maintenance.entity.VesselStatus;
import com.tfo.maintenance.excel.ExcelFormatter;
import com.tfo.maintenance.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ibm.java.diagnostics.utils.Context.logger;
import static com.ibm.jvm.dtfjview.Version.getName;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Slf4j
public class StatusController {

    @Autowired
    private VesselStatusRepository statusRepository;

    //Big method for creating,filling and sending csv file
    private void initCsvWriter(HttpServletResponse response, Collection<VesselStatus> vesselsList, String status) throws IOException, NoSuchFieldException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = status + "vessels" + currentDateTime + ".csv";

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(VesselStatus.class);

        HeaderColumnNameTranslateMappingStrategy<VesselStatus> hStrategy = new HeaderColumnNameTranslateMappingStrategy<VesselStatus>();


        String[] columns = new String[]{"imo", "id", "status", "vesselflag", "vesselname"};
        strategy.setColumnMapping(columns);
//        strategy.ignoreFields(new MultiValuedMap<VesselStatus.getName(),
//                VesselStatus.class.getField("vesselLink")> ());
        Map<String, String> columnsMap = new HashMap<String, String>();
        columnsMap.put("imo", "imo");
        columnsMap.put("id", "id");
        columnsMap.put("status", "status");

        hStrategy.setType(VesselStatus.class);
        hStrategy.setColumnMapping(columnsMap);

        //create a csv writer

        StatefulBeanToCsv writer = new StatefulBeanToCsvBuilder<VesselStatus>
                (response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
//                .withMappingStrategy(hStrategy)
//                .withMappingStrategy(strategy)
                .withOrderedResults(false)
                .build();



//        writer.write(columns);
        vesselsList.forEach(vessel -> {
            try {
                writer.write(vessel);
            } catch (CsvDataTypeMismatchException e) {
                e.printStackTrace();
            } catch (CsvRequiredFieldEmptyException e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/vesselStatus")
    public void exportCSV(HttpServletResponse response) throws Exception {
        //set file name and content type

        String active = "3";
        String startDate = "01.01.2020";
        String endDate = "12.12.2020";
        Collection<VesselStatus> vesselsList;
        String status;
        switch (active) {
            case "0":
                vesselsList = statusRepository.findInactive1
                        (DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "inactive";
                break;
            case "1":
                vesselsList = statusRepository.findActive1
                        (DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "active";
                break;
            default:
                vesselsList = statusRepository.findDeactivated1(DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "deactivated";
                break;
        }
//        vesselsList.forEach(vessel-> System.out.println(vessel.getVesselLink().getVesselName()));
        //write all vessels to csv file
        initCsvWriter(response, vesselsList, status);

    }


    @GetMapping("/vesselStatus/{startDate}/{endDate}/{active}")
    public void exportCSV0(@PathVariable(value = "active") String active, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response) throws Exception {

        Collection<VesselStatus> vesselsList;
        String status;
        switch (active) {
            case "0":
                vesselsList = statusRepository.findInactive1
                        (DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "inactive";
                break;
            case "1":
                vesselsList = statusRepository.findActive1
                        (DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "active";
                break;
            default:
                vesselsList = statusRepository.findDeactivated1(DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "deactivated";
                break;
        }
        //write all vessels to csv file
        initCsvWriter(response, vesselsList, status);

    }

    @GetMapping("/vesselStatus/{tenantId1}/{startDate}/{endDate}/{active}")
    public void exportCSV1(@PathVariable(value = "tenantId1") String tenantId1, @PathVariable(value = "active") String active, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response) throws Exception {
        Collection<VesselStatus> vesselsList;
        String status;

        switch (active) {
            case "0":
                vesselsList = statusRepository.findInactive2
                        (tenantId1, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "inactive";
                break;
            case "1":
                vesselsList = statusRepository.findActive2
                        (tenantId1, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "active";
                break;
            default:
                vesselsList = statusRepository.findDeactivated2(tenantId1, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "dectivated";
                break;

        }
        //write all vessels to csv file
        initCsvWriter(response, vesselsList, status);

    }

    @GetMapping("/vesselStatus/{tenantId1}/{tenantId2}/{startDate}/{endDate}/{active}")
    public void exportCSV2(@PathVariable(value = "tenantId1") String tenantId1, @PathVariable(value = "tenantId2") String tenantId2, @PathVariable(value = "active") String active, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response) throws Exception {
        Collection<VesselStatus> vesselsList;
        String status;

        switch (active) {
            case "0":
                vesselsList = statusRepository.findInactive3
                        (tenantId1, tenantId2, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "inactive";
                break;
            case "1":
                vesselsList = statusRepository.findActive3
                        (tenantId1, tenantId2, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "active";
                break;
            default:
                vesselsList = statusRepository.findDeactivated3(tenantId1, tenantId2, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
                status = "deactivated";
                break;

        }
        //write all vessels to csv file
        initCsvWriter(response, vesselsList, status);

    }
}
//    @GetMapping("/vesselStatus")
//    public ResponseEntity<Collection<Alerts>> getXlsReport(@PathVariable(value = "active") String active, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response)
//            throws ResourceNotFoundException {
//        logger.info("started vesselStatus api");
//        response.setContentType("application/octet-stream");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//        logger.info("date set");
//        String headerKey = "Content-Disposition";
//        //TODO filename based on vesselstatus
//        String headerValue = "attachment; filename=alerts" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//        logger.info("header set");
//        active = "0";
//        startDate="01.01.2020";
//        endDate="12.12.2020";
//
//
//        Collection<VesselStatus> vesselsList;
//        switch (active) {
//            case "0":
//                vesselsList = statusRepository.findInactive1
//                        (DateUtils.toDate(startDate), DateUtils.toDate(endDate));
//            case "1":
//                vesselsList = statusRepository.findActive1
//                        (DateUtils.toDate(startDate), DateUtils.toDate(endDate));
//            default:
//                vesselsList = statusRepository.findDeactivated1(DateUtils.toDate(startDate), DateUtils.toDate(endDate));
//        }
//
//
//        vesselsList.stream().forEach(vessel -> logger.info("found vessels : {}" + vessel.getImo());
//
//        logger.info("vessels status retrieved");
////        ExcelFormatter excelExporter = new ExcelFormatter(vesselsList);
//        try {
////            excelExporter.export(response);
//            File file = getReportService().generateReport(reportName);
//
//            return ResponseEntity.ok()
//                    .header("Content-Disposition", "attachment; filename=" + "status" + ".csv")
//                    .contentLength(file.length())
//                    .contentType(MediaType.parseMediaType("text/csv"))
//                    .body(new FileSystemResource(file));
//            logger.info("csv ready");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        throw new ResourceNotFoundException("Xls could not be generated ");
//    }
//
//    @GetMapping("/vesselStatus/{startDate}/{endDate}/{active}")
//    public ResponseEntity<Collection<Alerts>> getXlsReport(@PathVariable(value = "active") String active, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response)
//            throws ResourceNotFoundException {
//        logger.info("started vesselStatus api");
//        response.setContentType("application/octet-stream");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//        logger.info("date set");
//        String headerKey = "Content-Disposition";
//        //TODO filename based on vesselstatus
//        String headerValue = "attachment; filename=alerts" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//        logger.info("header set");
//
//        Collection<VesselStatus> vesselsList;
//        switch (active) {
//            case "0":
//                vesselsList = statusRepository.findInactive1
//                        (DateUtils.toDate(startDate), DateUtils.toDate(endDate));
//            case "1":
//                vesselsList = statusRepository.findActive1
//                        (DateUtils.toDate(startDate), DateUtils.toDate(endDate));
//            default:
//                vesselsList = statusRepository.findDeactivated1(DateUtils.toDate(startDate), DateUtils.toDate(endDate));
//        }
//
//
//        vesselsList.stream().forEach(vessel -> logger.info("found vessels : {}" + vessel.getImo());
//
//        logger.info("alerts data retrieved");
//        ExcelFormatter excelExporter = new ExcelFormatter(vesselsList);
//        try {
////            excelExporter.export(response);
//            File file = getReportService().generateReport(reportName);
//
//            return ResponseEntity.ok()
//                    .header("Content-Disposition", "attachment; filename=" + reportName + ".csv")
//                    .contentLength(file.length())
//                    .contentType(MediaType.parseMediaType("text/csv"))
//                    .body(new FileSystemResource(file));
//            logger.info("csv ready");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        throw new ResourceNotFoundException("Xls could not be generated ");
//    }
//
//    @GetMapping("/vesselStatus/{tenantId}/{startDate}/{endDate}")
//    public ResponseEntity<Collection<Alerts>> getXlsReport(@PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response)
//            throws ResourceNotFoundException {
//        logger.info("started xls api");
//        response.setContentType("application/octet-stream");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//        logger.info("date set");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=alerts" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//        logger.info("header set");
//        Collection<Alerts> alertsList = alertsRepository
//                .getReport1(tenantId, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
//        alertsList.stream().forEach(alert -> logger.info("found vessels : {} {}", alert.getImo(), alert.getAlertDate()));
//
//        logger.info("alerts data retrieved");
//        ExcelFormatter excelExporter = new ExcelFormatter(alertsList);
//        try {
//            excelExporter.export(response);
//            logger.info("excel ready");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        throw new ResourceNotFoundException("Xls could not be generated for ::" + tenantId);
//    }
//
//    @GetMapping("/vesselStatus/{tenantId1}/{tenantId2}/{startDate}/{endDate}")
//    public ResponseEntity<Collection<Alerts>> getXlsReport(@PathVariable(value = "tenantId1") String tenantId1, @PathVariable(value = "tenantId2") String tenantId2, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response)
//            throws ResourceNotFoundException {
//        logger.info("started xls api");
//        response.setContentType("application/octet-stream");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//        logger.info("date set");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=alerts" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//        logger.info("header set");
//        Collection<Alerts> alertsList = alertsRepository
//                .getReport2(tenantId1, tenantId2, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
//        alertsList.stream().forEach(alert -> logger.info("found vessels : {} {}", alert.getImo(), alert.getAlertDate()));
//
//        logger.info("alerts data retrieved");
//        ExcelFormatter excelExporter = new ExcelFormatter(alertsList);
//        try {
//            excelExporter.export(response);
//            logger.info("excel ready");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        throw new ResourceNotFoundException("Xls could not be generated for ::" + tenantId1 + tenantId2);
//    }

//}



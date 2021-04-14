package com.tfo.maintenance.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.tfo.maintenance.dao.analytics.AnalyticsRepository;
import com.tfo.maintenance.entity.Analytics;
import com.tfo.maintenance.entity.VesselStatus;
import com.tfo.maintenance.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AnalyticsController {
    @Autowired
    AnalyticsRepository analyticsRepository;

    private void initCsvWriter(HttpServletResponse response, Collection<Analytics> kpiList) throws IOException, NoSuchFieldException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "KPI" + currentDateTime + ".csv";

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Analytics.class);

//        String[] columns = new String[]{"tenantcode", "id", "StringValue", "branchId", "eventId"};
        String[] columns = new String[]{"tenantcode", "eventid", "ocrinprogress", "ocrcompleted", "refinementinprogress"};
        strategy.setColumnMapping(columns);

        StatefulBeanToCsv writer = new StatefulBeanToCsvBuilder<Analytics>
                (response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        System.out.println("passed writer init");

        kpiList.forEach(kpi -> {
            try {
                System.out.println(kpi.toString());
                writer.write(kpi);
            } catch (CsvDataTypeMismatchException e) {
                e.printStackTrace();
            } catch (CsvRequiredFieldEmptyException e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/Analytics")
    public void getKpi0(HttpServletResponse response) throws Exception {
        //set file name and content type

        String startDate = "01.01.2020";
        String endDate = "12.12.2020";
        Collection<Analytics> kpiList;
        System.out.println("started query");
        kpiList = analyticsRepository.getKpi0(DateUtils.toDate(startDate), DateUtils.toDate(endDate));
        System.out.println("finished query");

        //write all vessels to csv file
        initCsvWriter(response, kpiList);

    }

    @GetMapping("/Analytics/{startDate}/{endDate}")
    public void getKpi0(@PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response) throws Exception {
        //set file name and content type

        Collection<Analytics> kpiList;
        System.out.println("started query");
        kpiList = analyticsRepository.getKpi0(DateUtils.toDate(startDate), DateUtils.toDate(endDate));
        System.out.println("finished query");

        //write all vessels to csv file
        initCsvWriter(response, kpiList);

    }

    @GetMapping("/Analytics/{tenantId1}/{startDate}/{endDate}")
    public void getKpi1(@PathVariable(value = "tenantId1") String tenantId1, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response) throws Exception {
        //set file name and content type

        Collection<Analytics> kpiList;
        System.out.println("started query");
        kpiList = analyticsRepository.getKpi1(tenantId1, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
        System.out.println("finished query");

        //write all vessels to csv file
        initCsvWriter(response, kpiList);

    }

    @GetMapping("/Analytics/{tenantId1}/{tenantId2}/{startDate}/{endDate}")
    public void getKpi2(@PathVariable(value = "tenantId1") String tenantId1, @PathVariable(value = "tenantId2") String tenantId2, @PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate, HttpServletResponse response) throws Exception {
        //set file name and content type

        Collection<Analytics> kpiList;
        System.out.println("started query");
        kpiList = analyticsRepository.getKpi2(tenantId1, tenantId2, DateUtils.toDate(startDate), DateUtils.toDate(endDate));
        System.out.println("finished query");

        //write all vessels to csv file
        initCsvWriter(response, kpiList);

    }


}

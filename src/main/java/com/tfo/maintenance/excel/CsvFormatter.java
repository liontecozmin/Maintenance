package com.tfo.maintenance.excel;

import com.tfo.maintenance.entity.VesselStatus;

import java.io.*;
import java.util.Collection;

public class CsvFormatter {

    //European countries use ";" as
    //CSV separator because "," is their digit separator
    private static final String CSV_SEPARATOR = ",";

    private static void writeToCSV(Collection<VesselStatus> vesselList) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("vessels.csv"), "UTF-8"));
            for (VesselStatus vessel : vesselList) {
                StringBuffer oneLine = new StringBuffer();
//                oneLine.append(vessel.getId());
                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(vessel.getVesselName().trim());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(vessel.getImo());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (UnsupportedEncodingException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }



}

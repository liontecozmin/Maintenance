package com.tfo.maintenance.table;

public class CountryMapper {

    public static String getCountry(String tenantId) {
        if (tenantId.length() > 1) {
            int charToInt = Character.getNumericValue(tenantId.charAt(tenantId.length() - 1));
            switch (charToInt) {
                case 1:
                    return "Germany";
                case 2:
                    return "Italy";
                case 4:
                case 9:
                    return "Austria";
                default:
                    return "Unknown country";
            }
        }
        return "Unknown country";
    }

}

package com.avider.bks.utils;


import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruchiagarwal on 7/28/18.
 */
public class PatronCategoryComputation {
    //List of category codes defined

    //convention for books only plan
    //{number of books}-BOOK-{no of months}

//convention for books and magazine plan
    //{number of books}-BM-{no of magazines}-{no of months}
    private Map planToCategoryMap = new HashMap();

    public void initialize() {
        planToCategoryMap.put("1 BOOK","1-BOOK-");
        planToCategoryMap.put("1 BOOKS","1-BOOK-");
        planToCategoryMap.put("2 BOOKS","2-BOOK-");
        planToCategoryMap.put("3 BOOKS","3-BOOK-");
        planToCategoryMap.put("4 BOOKS","4-BOOK-");
        planToCategoryMap.put("6 BOOKS","6-BOOK-");
        planToCategoryMap.put("8 BOOKS","8-BOOK-");
        planToCategoryMap.put("10 BOOKS","10-BOOK-");


        planToCategoryMap.put("1 BOOK(1 BOOK&1 MAGAZINE)","1-BM-1-");
        planToCategoryMap.put("1 BOOK(1 BOOK&1 MAGAZINES)","1-BM-1-");
        planToCategoryMap.put("1 BOOK(1 BOOKS&1 MAGAZINE)","1-BM-1-");
        planToCategoryMap.put("1 BOOK(1 BOOKS&1 MAGAZINES)","1-BM-1-");
        planToCategoryMap.put("1 BOOK(1 BOOK&2 MAGAZINE)","1-BM-2-");
        planToCategoryMap.put("1 BOOK(1 BOOK&2 MAGAZINES)","1-BM-2-");
        planToCategoryMap.put("1 BOOK(1 BOOKS&2 MAGAZINE)","1-BM-2-");
        planToCategoryMap.put("1 BOOK(1 BOOKS&2 MAGAZINES)","1-BM-2-");

        planToCategoryMap.put("2 BOOKS(2 BOOKS&1 MAGAZINE)","2-BM-1-");
        planToCategoryMap.put("2 BOOKS(2 BOOKS&1 MAGAZINES)","2-BM-1-");
        planToCategoryMap.put("2 BOOKS(2 BOOKS&2 MAGAZINE)","2-BM-2-");
        planToCategoryMap.put("2 BOOKS(2 BOOKS&2 MAGAZINES)","2-BM-2-");
        planToCategoryMap.put("2 BOOKS(2 BOOK&1 MAGAZINE)","2-BM-1-");
        planToCategoryMap.put("2 BOOKS(2 BOOK&1 MAGAZINES)","2-BM-1-");
        planToCategoryMap.put("2 BOOKS(2 BOOK&2 MAGAZINE)","2-BM-2-");
        planToCategoryMap.put("2 BOOKS(2 BOOK&2 MAGAZINES)","2-BM-2-");

        planToCategoryMap.put("3 BOOKS(3 BOOKS&1 MAGAZINE)","3-BM-1-");
        planToCategoryMap.put("3 BOOKS(3 BOOKS&1 MAGAZINES)","3-BM-1-");
        planToCategoryMap.put("3 BOOKS(3 BOOKS&2 MAGAZINE)","3-BM-2-");
        planToCategoryMap.put("3 BOOKS(3 BOOKS&2 MAGAZINES)","3-BM-2-");
        planToCategoryMap.put("3 BOOKS(3 BOOK&1 MAGAZINE)","3-BM-1-");
        planToCategoryMap.put("3 BOOKS(3 BOOK&1 MAGAZINES)","3-BM-1-");
        planToCategoryMap.put("3 BOOKS(3 BOOK&2 MAGAZINE)","3-BM-2-");
        planToCategoryMap.put("3 BOOKS(3 BOOK&2 MAGAZINES)","3-BM-2-");

        planToCategoryMap.put("4 BOOKS(4 BOOKS&1 MAGAZINE)","4-BM-1-");
        planToCategoryMap.put("4 BOOKS(4 BOOKS&1 MAGAZINES)","4-BM-1-");
        planToCategoryMap.put("4 BOOKS(4 BOOKS&2 MAGAZINE)","4-BM-2-");
        planToCategoryMap.put("4 BOOKS(4 BOOKS&2 MAGAZINES)","4-BM-2-");
        planToCategoryMap.put("4 BOOKS(4 BOOK&1 MAGAZINE)","4-BM-1-");
        planToCategoryMap.put("4 BOOKS(4 BOOK&1 MAGAZINES)","4-BM-1-");
        planToCategoryMap.put("4 BOOKS(4 BOOK&2 MAGAZINE)","4-BM-2-");
        planToCategoryMap.put("4 BOOKS(4 BOOK&2 MAGAZINES)","4-BM-2-");

        planToCategoryMap.put("6 BOOKS(6 BOOKS&1 MAGAZINE)","6-BM-1-");
        planToCategoryMap.put("6 BOOKS(6 BOOKS&1 MAGAZINES)","6-BM-1-");
        planToCategoryMap.put("6 BOOKS(6 BOOKS&2 MAGAZINE)","6-BM-2-");
        planToCategoryMap.put("6 BOOKS(6 BOOKS&2 MAGAZINES)","6-BM-2-");
        planToCategoryMap.put("6 BOOKS(6 BOOKS&3 MAGAZINE)","6-BM-3-");
        planToCategoryMap.put("6 BOOKS(6 BOOKS&3 MAGAZINES)","6-BM-3-");
        planToCategoryMap.put("6 BOOKS(6 BOOK&1 MAGAZINE)","6-BM-1-");
        planToCategoryMap.put("6 BOOKS(6 BOOK&1 MAGAZINES)","6-BM-1-");
        planToCategoryMap.put("6 BOOKS(6 BOOK&2 MAGAZINE)","6-BM-2-");
        planToCategoryMap.put("6 BOOKS(6 BOOK&2 MAGAZINES)","6-BM-2-");
        planToCategoryMap.put("6 BOOKS(6 BOOK&3 MAGAZINE)","6-BM-3-");
        planToCategoryMap.put("6 BOOKS(6 BOOK&3 MAGAZINES)","6-BM-3-");

        planToCategoryMap.put("MONTHLY WAIVER PLAN", "WV1");
        planToCategoryMap.put("MEMBERSHIP PLAN", "2-BOOK-");




    }
//logic of dormant needs to be applied
    public String computePatronCategoryForAJBPlan(String JBPlan, LocalDate dateExpiry, LocalDate startDate, LocalDate memberDataExportDate, String memberStatus) {

        String patronCategory = (String)planToCategoryMap.get(JBPlan.toUpperCase());
        return patronCategory;


        //dont compute the months - actual plan

/*
        int numOfMonths =1;  //default

        long expiryDaysFromExport = ChronoUnit.DAYS.between(memberDataExportDate, dateExpiry);
        //System.out.println("expiryDaysFromExport " + expiryDaysFromExport);

        Period expiryFromStart = Period.between(startDate, dateExpiry);
        long totalMemberShipDuration = ChronoUnit.DAYS.between(startDate, dateExpiry);
       // System.out.println("totalMemberShipDuration in days " + totalMemberShipDuration);
        long membershipPeriodInMonths = expiryFromStart.getMonths();
//        System.out.println("totalMemberShipDuration in months new " + membershipPeriodInMonths);

        long periodElapsedSoFar = ChronoUnit.DAYS.between(startDate, memberDataExportDate);

        if(totalMemberShipDuration <60) {
            //System.out.println("1");
            numOfMonths = 1;
        } else if(totalMemberShipDuration <100) {
            //System.out.println("2");
            numOfMonths = 3;
        } else if(expiryDaysFromExport > 190) {// number of months remaing in expiry is more than 6 months
            //System.out.println("3");
            numOfMonths = 12;
        } else if(expiryDaysFromExport >= 95) {// number of months remaining in expiry is more than 3 months but less than 6 months
            //System.out.println("4");
            if(membershipPeriodInMonths >= 6) {
                numOfMonths = 6;
            } else {
                numOfMonths = 3;
            }
        } else if(expiryDaysFromExport >= 32) {// number of months remaining in expiry is more than 1 months but less than 3 months
            //System.out.println("5");
            if(membershipPeriodInMonths >= 3) {
                numOfMonths = 3;
            } else {
                numOfMonths = 1;
            }
        } else {//cases remaining- total membership duration >=100 && expiryDaysFromExport <= 31
            //System.out.println("6");
            if (membershipPeriodInMonths % 12 == 0 && membershipPeriodInMonths >= 12) {
                numOfMonths = 12;
            } else if (membershipPeriodInMonths % 6 == 0 && membershipPeriodInMonths >= 6) {
                numOfMonths = 6;
            } else if (membershipPeriodInMonths % 3 == 0 && membershipPeriodInMonths >= 3) {
                numOfMonths = 3;
            } else if (membershipPeriodInMonths >= 12 && (JBPlan.startsWith("4") || JBPlan.startsWith("6") || JBPlan.startsWith("8") || JBPlan.startsWith("10"))) {// higher plans usually go for 1 year
                numOfMonths = 12;
            } else {
                numOfMonths = 1;
            }
        }

        String computedPatronCategory = "";
        System.out.println(JBPlan);

        if(!patronCategory.equals("WV1")) {
            computedPatronCategory = patronCategory + numOfMonths;
        } else {
            computedPatronCategory = patronCategory;
        }
        return computedPatronCategory;*/

    }


    private int monthsDifference(LocalDate startDate, LocalDate endDate) {
        int monthDiff = 0;
        int startMonth = startDate.getMonth().getValue();
        int startYear = startDate.getYear();

        int endMonth = endDate.getMonth().getValue();
        int endYear = endDate.getYear();

        if(endMonth >=startMonth) {
            monthDiff = ((endYear - startYear) *12) + (endMonth-startMonth);
        } else {
            monthDiff = (((endYear - startYear)-1) *12) + (12-endMonth)+startMonth;
        }
        return monthDiff;

    }

}

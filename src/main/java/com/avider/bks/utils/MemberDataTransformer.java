package com.avider.bks.utils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by root on 27/7/18.
 */
public class MemberDataTransformer {

    public void transformFile(String patronExportFilePath, String outputFilePath, LocalDate dtDataExported) {
        try {
            File readfile = new File(patronExportFilePath);
            BufferedReader fileReader = new BufferedReader(new FileReader(readfile));
            File writefile = new File(outputFilePath);
            FileWriter writer = new FileWriter(writefile, true);
            String recievedline = null;
            PatronCategoryComputation patronCategoryComputation = new PatronCategoryComputation();
            patronCategoryComputation.initialize();
            //  int i = 0;
            fileReader.readLine(); // skip the header line
            writer.append("\n");
            while ((recievedline = fileReader.readLine()) != null) {
//i++;



                String[] memberDataLine = recievedline.split(",");
                String line = "";
                //replace in cell commas with semicolons
                boolean continuation = false;
                for(String data:memberDataLine) {

                    //System.out.println(":"+data+":" + data.contains("\"") );

                    if(data.startsWith("\"") && !continuation) {

                        line = line + data.replaceAll("\"", "");
                        continuation = true;
                    } else if(continuation) {

                        if(data.endsWith("\"")) {
                            //System.out.println(data.charAt(0));
                            line = line + ";" + data.replaceAll("\"", "") + ",";
                            continuation = false;
                        } else {
                            line = line + ";" + data;
                        }
                    } else {
                        line = line +  data + ",";
                    }


                }

                memberDataLine = line.split(",");
                if(memberDataLine.length > 5) {
                    String surname ="";
                    String firstName = "";
                    if(memberDataLine[2].isEmpty()) {
                        String[] names = memberDataLine[1].split(" ");
                        if (names.length >= 2) {
                            //firstName = names[0];
                            surname = names[names.length-1];
                            firstName = memberDataLine[1].substring(0,memberDataLine[1].lastIndexOf(" ") );
                        } else {
                            surname = memberDataLine[1];
                            firstName = "";
                        }
                    } else {
                        surname = memberDataLine[2];
                        firstName = memberDataLine[1];
                    }
                    String comma = ",";
                    String card = memberDataLine[0];
                    String emptyString = ",";
                    //String address =   memberDataLine[11];
                    System.out.println("recievedline : " + recievedline);
                    System.out.println("line : " + line);
                    String phone =memberDataLine[8];
                    String mobile = memberDataLine[3];
                    String email = memberDataLine[4];

                    String dateExpiry =  memberDataLine[7];
                    System.out.println("datexpiry " + dateExpiry);
                    String[] dateExpArr = dateExpiry.split("/");
                    LocalDate dtDateExpiry = LocalDate.of(Integer.parseInt(dateExpArr[2]),Integer.parseInt(dateExpArr[0]),Integer.parseInt(dateExpArr[1]));

                    LocalDate adjustedExpiry = dtDateExpiry.minusDays(1);
                    String adjustedDateExpiry = adjustedExpiry.format(DateTimeFormatter.ofPattern("LL/dd/uuuu"));
                    String dateStarted =  memberDataLine[10];

                    //System.out.println("memberdataline" + line);
                    //System.out.println("datexpiry" + dateStarted);
                    String[] dateStartedArr = dateStarted.split("/");
                    LocalDate dtDateStarted = LocalDate.of(Integer.parseInt(dateStartedArr[2]),Integer.parseInt(dateStartedArr[0]),Integer.parseInt(dateStartedArr[1]));
                    dateStarted = dtDateStarted.format(DateTimeFormatter.ofPattern("LL/dd/uuuu"));


                    String updatedOn = "";
                    String securityDeposit = "0";

                    String ddOpted =  computeDDOpted(memberDataLine[9]);
                    //String memberStatus =  memberDataLine[6].toUpperCase();
                    String memberStatus =  "ACTIVE"; // all active
                    String jbPlan = memberDataLine[5];

                    String patronCategory = patronCategoryComputation.computePatronCategoryForAJBPlan(jbPlan,dtDateExpiry,dtDateStarted,dtDataExported,memberStatus);

                    writer.append(new StringBuilder().append(card)
                            .append(comma + surname)
                            .append(comma + firstName)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(comma + computeAddress(memberDataLine))
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(comma + email)
                            .append(comma + phone)
                            .append(comma + mobile)
                            //15 empty columns
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(comma + patronCategory)

                            .append(comma + "'" + dateStarted + "'")
                            .append(comma + "'" + adjustedDateExpiry + "'")
                            //followed by 28 empty columns
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)

                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(comma + mobile)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(comma + updatedOn)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(emptyString)
                            .append(comma + securityDeposit)
                            .append(comma + ddOpted)
                            .append(comma + memberStatus)
                            .append("\n"));

                }
                writer.flush();
            }
            fileReader.close();

            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException( ex);
        }

    }

    private String computeAddress(String[] memberDataLine) {

        String addressToReturn = "";
        for(int i=11;i < memberDataLine.length;i++) {
            addressToReturn = addressToReturn + memberDataLine[i] + ";";
        }
        return addressToReturn;
        /*String address2ToReturn = "";
        String address2InSrc = memberDataLine[9];
        String address3InSrc = memberDataLine[10];
        String localityInSrc = memberDataLine[11];
        if(address2InSrc.isEmpty() && address3InSrc.isEmpty() && localityInSrc.isEmpty()) {
            return address2ToReturn;
        } else {
            address2ToReturn = address2InSrc +";" + address3InSrc + ";" + localityInSrc;
            //if address2insrc was empty
            if(address2ToReturn.startsWith(";")) {
                address2ToReturn.substring(1);
                //if address3insrc was also empty
                if(address2ToReturn.startsWith(";")) {
                    address2ToReturn.substring(1);
                }

            }

            //if locality as empty
            if(address2ToReturn.endsWith(";")) {
                address2ToReturn.substring(0, address2ToReturn.length() - 1);
                //if address3insrc was also empty
                if(address2ToReturn.endsWith(";")) {
                    address2ToReturn.substring(0, address2ToReturn.length() - 1);
                }
            }

        }
        return address2ToReturn;*/


    }

    private String computeDDOpted(String ddOptedField) {
        if(ddOptedField == null || ddOptedField.equals("")) {
            return "0";
        } else {
            if(ddOptedField.toUpperCase().equals("YES") || ddOptedField.toUpperCase().startsWith("Y")) {
                return "1";
            } else {
                return "0";
            }
        }
    }


    public static void main(String[] args) {
        //No line except the header line should be there in file.
        //dates should be in form m/d/yyyy in export file
        //layout of the file is like below ( so that commas in address fields can be effectively taken care of
        //Membership No	First Name	Last Name	Mobile	Email Id	Plan	Status	Expiry Date	   Landline	  DD Opted	 Start Date   Address 1	Address 2	Address 3	Locality	City	Pincode
        //MEMBERSHIP PLAN (web transfer) are defaulted to 2 books plan
        //replace /r regex characters with space in whole excel
        // all 5 books plans need to be changed to 6 books plan
        // Delee the rows with interbranch transfer plan
        // first row will contain the header. second row onwards is ther data
        String memberExportFilePath="/Users/ruchiagarwal/avidreaders/May5MemberData_for_migration.csv";
        String outputFilePath ="/Users/ruchiagarwal/avidreaders/patron_test_import.csv";
        LocalDate dateOfExport = LocalDate.of(2018,5,5);

        new MemberDataTransformer().transformFile(memberExportFilePath,outputFilePath,dateOfExport);

    }

}

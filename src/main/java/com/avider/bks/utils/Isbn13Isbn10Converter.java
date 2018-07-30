package com.avider.bks.utils;

/**
 * Created by ruchiagarwal on 7/24/18.
 */
public class Isbn13Isbn10Converter {

    public String convertFromIsbn13(String isbn13) {
        char[] isbn13Chars = isbn13.toCharArray();
        int[] isbn13Ints = new int[13];
        int[] isbn10Ints = new int[10];
        char[] isbn10Chars = new char[10];
        int weightedSum =0;

        //taking digits 4 to 12 as they will be part of 10 digit number as well
        for (int i=3, j=10;i<=11;i++,j--) {
            isbn13Ints[i]=Integer.parseInt(String.valueOf(isbn13Chars[i]));
            isbn10Ints[i-3] = isbn13Ints[i];
            weightedSum = weightedSum + isbn13Ints[i]*j;


            
        }

        int mod11 = weightedSum % 11;

        int distanceFrom11Divisibility = 11 - mod11;
        if(mod11 == 0) {
            distanceFrom11Divisibility = 0;
        }
        isbn10Ints[9] = distanceFrom11Divisibility;
        for(int i=0; i<10; i++) {

            isbn10Chars[i]=String.valueOf(isbn10Ints[i]).charAt(0);

        }
        return new String(isbn10Chars);





    }

    public static void main(String[] args) {
        String isbn13 ="9788172236656";
        String isbn10 = new Isbn13Isbn10Converter().convertFromIsbn13(isbn13);
        System.out.println("\n"+isbn13 + "::::" + isbn10);
    }
}

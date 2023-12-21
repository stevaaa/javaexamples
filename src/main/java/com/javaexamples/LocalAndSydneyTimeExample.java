package com.javaexamples;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class LocalAndSydneyTimeExample {
//    private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a";

    public static void main(String[] args) {

        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Australia/Sydney"));
        System.out.println(ldt);//from  w w w .  j a v  a2 s. c  om
//
//        ZoneId usCentral = ZoneId.of("America/Chicago");
//        ZonedDateTime zdt = ZonedDateTime.of(ldt, usCentral);
//        System.out.println("In US Central Time Zone:" + zdt);
//
//        ZoneId asiaKolkata = ZoneId.of("Asia/Kolkata");
//        ZonedDateTime zdt2 = zdt.withZoneSameInstant(asiaKolkata);
//        System.out.println("In Asia/Kolkata Time Zone:" + zdt2);
//
//        ZonedDateTime zdt3 = zdt.withZoneSameInstant(ZoneId.of("Z"));
//        System.out.println("In UTC Time Zone:" + zdt3);

    }
}

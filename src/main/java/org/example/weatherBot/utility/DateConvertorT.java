package org.example.weatherBot.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertorT {
     public static String toNormal (long dt){
         Date date = new java.util.Date(dt*1000L);
         SimpleDateFormat simpleFormat= new SimpleDateFormat("dd.MM.yyyy HH:mm");
         return simpleFormat.format(date);
     }
}

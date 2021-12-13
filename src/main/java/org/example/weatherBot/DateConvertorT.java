package org.example.weatherBot;

import java.text.SimpleDateFormat;
import java.util.Date;

class DateConvertorT {
     static String toNormal (long dt){
         Date date = new java.util.Date(dt*1000L);
         SimpleDateFormat simpleFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
         return simpleFormat.format(date);
     }
}

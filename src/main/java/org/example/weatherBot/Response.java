package org.example.weatherBot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.example.weatherBot.structure.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
class Response {
   private List<Weather> weather;
   private MainInfo main;
   private Wind wind;
   private Cloud clouds;
   private SystemT sys;
   private long dt;
   private  int timezone;
   private String name;
}

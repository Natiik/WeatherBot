package org.example.weatherBot.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.example.weatherBot.response.structure.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Response {
   private List<Weather> weather;
   private Info main;
   private Wind wind;
   private Cloud clouds;
   private SystemT sys;
   private long dt;
   private  int timezone;
   private String name;
}

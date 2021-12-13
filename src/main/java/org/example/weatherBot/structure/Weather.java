package org.example.weatherBot.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}

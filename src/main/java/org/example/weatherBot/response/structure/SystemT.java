package org.example.weatherBot.response.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SystemT {
    private long sunrise;
    private long sunset;
}

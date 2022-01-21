package org.example.weatherBot.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "setting")
public class Setting {


    @Id
    @Column(name = "id")
    private Long id;

    @Column (name = "metrics")
    private String metrics;

    @Column (name = "language")
    private String language;

    @Column(name = "location")
    private Long location;

    public Setting(Long id, String metrics, String language, Long location) {
        this.id = id;
        this.metrics = metrics;
        this.language = language;
        this.location = location;
    }

    public Setting() {
    }
}

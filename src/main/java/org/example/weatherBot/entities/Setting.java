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
    @Column(name = "Id")
    private Long id;

    @Column (name = "Metrics")
    private String metrics;

    @Column (name = "Lang")
    private String language;

    @Column(name = "Loc_id")
    private Long location;
}

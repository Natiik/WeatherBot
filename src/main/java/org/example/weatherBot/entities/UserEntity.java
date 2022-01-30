package org.example.weatherBot.entities;


import lombok.Getter;
import lombok.Setter;
import org.example.weatherBot.entities.entity_structure.Language;
import org.example.weatherBot.entities.entity_structure.Metrics;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {


    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "metrics")
    @Enumerated(EnumType.STRING)
    private Metrics metrics;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "location")
    private Long location;

    public UserEntity(Long id, String metrics, String language, Long location) {
        this.id = id;
        this.metrics = Metrics.valueOf(metrics.toUpperCase());
        this.language = Language.valueOf(language.toUpperCase());
        this.location = location;
    }

    public UserEntity() {
    }

    public void setMetrics(String value) {
        this.metrics = Metrics.valueOf(value.toUpperCase());
    }

    public void setLanguage(String value) {
        this.language = Language.valueOf(value.toUpperCase());
    }
}

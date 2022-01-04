package org.example.weatherBot.settingObject;

public enum Metrics {
    STANDARD("standard"),
    METRIC ("metric"),
    IMPERIAL("imperial");
    private  String i;

    Metrics(String string) {
        this.i=string;
    }
}

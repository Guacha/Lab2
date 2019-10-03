/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guacha.lab2;

/**
 *
 * @author Guacha
 */
public enum TypeSommet {
    STATION_BUS,
    STATION_METRO,
    STATION_BUSDENUIT,
    STATION_MULTI;
    
    
    public static TypeSommet parseType(String s) {
        switch (s) {
            case "STATION_BUS":
                return STATION_BUS;
            case "STATION_METRO":
                return STATION_METRO;
            case "STATION_BUSDENUIT":
                return STATION_BUSDENUIT;
            case "STATION_MULTI":
                return STATION_MULTI;
            default:
                return STATION_BUS;
        }
    }
}

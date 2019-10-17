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
public enum Ligne {
    LIGNE_VERTE,
    LIGNE_ORANGE,
    LIGNE_BLEUE,
    LIGNE_JAUNE;
    
    
    public static Ligne parseType(String s) {
        switch (s) {
            case "LIGNE_VERTE":
                return LIGNE_VERTE;
            case "LIGNE_ORANGE":
                return LIGNE_ORANGE;
            case "LIGNE_BLEUE":
                return LIGNE_BLEUE;
            case "LIGNE_JAUNE":
                return LIGNE_JAUNE;
            default:
                return LIGNE_VERTE;
        }
    }
}

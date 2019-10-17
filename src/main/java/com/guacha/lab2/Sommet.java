/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guacha.lab2;

import java.awt.Point;

/**
 *
 * @author Guacha
 */
public class Sommet {
    String nombre;
    Point pos;
    Ligne ligne;

    public Sommet(String nombre, Point p, Ligne t) {
        this.nombre = nombre;
        this.pos = p;
        this.ligne = t;
    }

    public Sommet(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}

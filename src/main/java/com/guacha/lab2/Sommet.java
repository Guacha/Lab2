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
    TypeSommet type;

    public Sommet(String nombre, Point p, TypeSommet t) {
        this.nombre = nombre;
        this.pos = p;
        this.type = t;
    }

    public Sommet(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}

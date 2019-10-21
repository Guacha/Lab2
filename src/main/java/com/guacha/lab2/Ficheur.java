/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guacha.lab2;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Guacha
 */
public class Ficheur {
    JFileChooser jfc;    
    
    public void sauvegarder(Graphe g) {
        jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("Archivos de mapa por grafo *.gfm", "gfm"));
        jfc.setAcceptAllFileFilterUsed(false);
        String path;
        if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            if (jfc.getSelectedFile().getAbsolutePath().endsWith(".gfm")) {
                path = jfc.getSelectedFile().getAbsolutePath();
            } else {
                path = jfc.getSelectedFile().getAbsolutePath().concat(".gfm");
            }
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(new File(path)));
                for (Map.Entry<Sommet, Map<Sommet, Integer>> entry : g.getSommAdj().entrySet()) {
                    Sommet s = entry.getKey();
                    writer.printf("%s" + ":" + "%s" + ":" + "%d" + ":" + "%d" + "\n", s.nombre, 
                            s.ligne, s.pos.x, s.pos.y);
                }
                
                writer.print("CONNECTIONS\n");
                for (Map.Entry<Sommet, Map<Sommet, Integer>> entry : g.getSommAdj().entrySet()) {
                    Sommet s = entry.getKey();
                    writer.printf(s.nombre + "\n");
                    for (Map.Entry<Sommet, Integer> con : entry.getValue().entrySet()) {
                        writer.printf("\t" + "%s" + ":" + "%d" + "\n", con.getKey().nombre, con.getValue());
                    }
                    writer.print("FIN\n");
                }
                writer.print("FIN-ALGORITMO");
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Ficheur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public Graphe ouvrir() {
        Graphe g = new Graphe();
        jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("Archivos de mapa por grafo *.gfm", "gfm"));
        jfc.setAcceptAllFileFilterUsed(false);
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(jfc.getSelectedFile()));
                String line = reader.readLine();
                while(!line.equals("CONNECTIONS")) {
                    Object[] sommData = line.split(":");
                    Point p = new Point(Integer.parseInt((String)sommData[2]), Integer.parseInt((String)sommData[3]));
                    Sommet s = new Sommet((String)sommData[0], p, Ligne.parseType((String)sommData[1]));
                    g.addVert(s);
                    line = reader.readLine();
                }
                
                line = reader.readLine();
                while (!line.equals("FIN-ALGORITMO")) {
                    Sommet act = g.getSommet(line);
                    line = reader.readLine();
                    while(!line.equals("FIN")) {
                        Object[] conData = line.substring(1).split(":");
                        g.addArete(act, g.getSommet((String)conData[0]), Integer.parseInt((String)conData[1]));
                        line = reader.readLine();
                    }
                    line = reader.readLine();
                }
                reader.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Ficheur.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Ficheur.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
        }
        g.soutGraphe();
        return g;
    }

    Graphe ouvrir(String path) {
        Graphe g = new Graphe();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while(!line.equals("CONNECTIONS")) {
                Object[] sommData = line.split(":");
                Point p = new Point(Integer.parseInt((String)sommData[2]), Integer.parseInt((String)sommData[3]));
                Sommet s = new Sommet((String)sommData[0], p, Ligne.parseType((String)sommData[1]));
                g.addVert(s);
                line = reader.readLine();
            }

            line = reader.readLine();
            while (!line.equals("FIN-ALGORITMO")) {
                Sommet act = g.getSommet(line);
                line = reader.readLine();
                while(!line.equals("FIN")) {
                    Object[] conData = line.substring(1).split(":");
                    g.addArete(act, g.getSommet((String)conData[0]), Integer.parseInt((String)conData[1]));
                    line = reader.readLine();
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ficheur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ficheur.class.getName()).log(Level.SEVERE, null, ex);
        }

        return g;
    }
    
    
}

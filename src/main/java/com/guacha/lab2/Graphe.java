/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guacha.lab2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Guacha
 */
public class Graphe {
    /*Okay, tengo que explicar lo que es un Mapa, si quieres leer mi explicación
      pues prepárate para confundirte un poco porque no se explicar, pero bueno
      ahí va :v
    
      En esencia, como nosotros entendemos la adyacencia en un grafo se puede
      representar como una multilista de ayacencia, en la que se tienen verticalmente
      los vértices, y horizontalmente los vértices con los que se conecta
    
      Entonces, en este caso, podemos usar la estructura de Java llamada Mapa, 
      un mapa es una estructura de la forma Map<K, V>, que en esencia lo que quiere
      decir, es un conjunto de llaves (K), a las que se le asocia un valor (V),
      entonces si lo comparamos con nuestra multilista, podemos hacer un conjunto de llaves
      que no se repiten (Los vértices) y a cada vértice se le asocia un conjunto de
      valores que representan las aristas
    
      Para emular esta estructura en Grafos con peso, fue necesario hacer en esencia
      un mapa de mapas (lo se, es confuso), porque en cada mapa, una llave K solo puede
      tener un valor asociado, sin embargo, si ese valor a su vez es otro mapa, entonces
      cada llave tendrá asociada un conjunto <K, V> donde K será el vértice con el que
      es adyacente, y V, será el valor del peso entre ellos.
    
      Una representación visual sería algo así:
    
        [Nodo A] -> {[Nodo B, 5], [Nodo C, 5]}
            |
            |
        [Nodo B] -> {[Nodo A, 5], [Nodo C, 5]}
            |
            |
        [Nodo C] -> {[Nodo A, 5], [Nodo B, 5]}
    
        Esto generaría un triángulo equilatero con sus tres aristas de peso 5
    */
    private final Map<Sommet, Map<Sommet, Integer>> sommAdj;

    public Graphe() {
        this.sommAdj = new HashMap<>();
    }

    public Map<Sommet, Map<Sommet, Integer>> getSommAdj() {
        return sommAdj;
    }
    
    void addVert(Sommet s) {
        sommAdj.putIfAbsent(s, new HashMap<>());
    }
    
    void quitVert(Sommet v) {
        sommAdj.values().forEach((value) -> {
            value.remove(v);
        });
        sommAdj.remove(v);
    }
    
    Sommet getSommet(String nom) {
        for (Sommet sommet : sommAdj.keySet()) {
            if (sommet.nombre.equals(nom)) {
                return sommet;
            }
        }
        return null;
    }
    
    void addArete(Sommet V1, Sommet V2, int poids) {
        sommAdj.get(V1).putIfAbsent(V2, poids);
        sommAdj.get(V2).putIfAbsent(V1, poids);
    }
    
    void quitArete(Sommet V1, Sommet V2) {
        Map aV1 = sommAdj.get(V1), aV2 = sommAdj.get(V2);
        
        if (aV1 != null) {
            aV1.remove(V2);
        }
        if (aV2 != null) {
            aV2.remove(V1);
        }
    }
    
    String soutGraphe(Sommet s) {
        StringBuilder sb = new StringBuilder();
        sb.append("Conexiones de ").append(s.nombre).append("\n");
        if (sommAdj.get(s).isEmpty()) {
            sb.append("\tNo existen conexiones");
        } else {
            for (Map.Entry<Sommet, Integer> entry : sommAdj.get(s).entrySet()) {
                sb.append("\t").append("Conexión con ").append(entry.getKey().nombre).append(" Con peso: ").append(entry.getValue());
                sb.append("\n");
            }
        }
        return sb.toString();
    }
    
    String soutGraphe() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Sommet, Map<Sommet, Integer>> entry : sommAdj.entrySet()) {
            sb.append("Conexiones de ").append(entry.getKey().nombre).append(": ");
            for (Map.Entry<Sommet, Integer> con : entry.getValue().entrySet()) {
                sb.append("\t").append(con.getKey().nombre).append(" Con peso: ").append(con.getValue());
                sb.append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    boolean existArete(Sommet edit, Sommet rel) {
        return sommAdj.get(edit).keySet().stream().anyMatch((somm) -> (somm.equals(rel)));
    }

    int getPoids(Sommet edit, Sommet rel) {
        for (Map.Entry<Sommet, Integer> entry : sommAdj.get(edit).entrySet()) {
            if (entry.getKey().equals(rel)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    void setPoids(Sommet edit, Sommet rel, int i) {
        for (Map.Entry<Sommet, Integer> entry : sommAdj.get(edit).entrySet()) {
            if (entry.getKey().equals(rel)) {
                entry.setValue(i);
            }
        }
    }
    
    List<Sommet> dijkstra(Sommet start, Sommet fin) {
        Map<Sommet, Map<Sommet, Integer>> dejaSels = new HashMap<>();
        HashMap aux = new HashMap<>();
        aux.put(start, 0);
        dejaSels.putIfAbsent(start, aux);
        
        for (int i = 0; i < sommAdj.size()-1; i++) {
            
        }
    }
    
    
    
}

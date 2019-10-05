/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guacha.lab2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    
    /**
     * Añade el vértice ingresado a la lista de vértices del grafo
     * @param s El vértice a añadir
     */
    public void addVert(Sommet s) {
        sommAdj.putIfAbsent(s, new HashMap<>());
    }
    
    /**
     * Borra el vértice ingresado de la lista de vertices del grafo <p>
     * recíprovcamente, se borran todas sus conexiones con vértices adyacentes
     * a él
     * @param v Instancia del vértice que se desee eliminar
     */
    public void quitVert(Sommet v) {
        sommAdj.values().forEach((value) -> {
            value.remove(v);
        });
        sommAdj.remove(v);
    }
    
    /**
     * Busca un vértice que tenga el nombre ingresado en la lista de vértices
     * del grafo
     * @param nom el nombre del vertice que se deasee buscar
     * @return El vértice que tenga el <b>nom</b> ingresado. Nulo si no existe 
     * un vértice con ese nombre
     */
    public Sommet getSommet(String nom) {
        for (Sommet sommet : sommAdj.keySet()) {
            if (sommet.nombre.equals(nom)) {
                return sommet;
            }
        }
        return null;
    }
    
    /**
     * Añade una conexión bidireccional entre los dos vértices ingresados en la
     * lista de adyacencia
     * @param V1 El primer vértice de la conexión
     * @param V2 El segundo vértice de la conexión
     * @param poids El peso de la arista creada entre los dos vértices. Siempre 
     * es > 0
     */
    public void addArete(Sommet V1, Sommet V2, int poids) {
        sommAdj.get(V1).putIfAbsent(V2, poids);
        sommAdj.get(V2).putIfAbsent(V1, poids);
    }
    
    /**
     * Elimina una arista entre los dos vértices ingresados (si existe)
     * @param V1 El primer vértice conectado
     * @param V2 El segundo vértice conectado
     */
    public void quitArete(Sommet V1, Sommet V2) {
        Map aV1 = sommAdj.get(V1), aV2 = sommAdj.get(V2);
        
        if (aV1 != null) {
            aV1.remove(V2);
        }
        if (aV2 != null) {
            aV2.remove(V1);
        }
    }
    
    /**
     * Función que obtiene toda la información pertinente del vértice ingresado 
     * y la organiza en una cadena escribible
     * @param s El vértice del que se desea escribir la información
     * @return Una cadena escribible que contiene toda la información organizada 
     * del vértice
     */
    public String soutGraphe(Sommet s) {
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
    
    /**
     * Función que obtiene toda la información pertinente de cada vértice del 
     * grafo, la junta y la organiza en una cadena escribible
     * @return Una cadena escribible que contiene toda la información del grafo
     */
    public String soutGraphe() {
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

    /**
     * Verifica si existe una arista entre los vértices A y B <p>
     * Para propósitos de este grafo, A y B son completamente intercambiables, 
     * ado que el grafo es no dirigido.
     * @param a Uno de los vértices a comparar
     * @param b El otro vértice a comparar
     * @return True si existe una arista entre A y B, False si no
     */
    public boolean existArete(Sommet a, Sommet b) {
        return sommAdj.get(a).keySet().stream().anyMatch((somm) -> (somm.equals(b)));
    }
    
    /**
     * Retorna el peso de la arista entre A y B (Si existe). <p>
     * Para propósitos de este grafo, A y B son completamente intercambiables, 
     * ado que el grafo es no dirigido.
     * @param A El vértice origen de la arista
     * @param B El vértice destino de la arista
     * @return El peso de la arista entre A y B. Retorna 0 si la arista no existe
     */
    public int getPoids(Sommet A, Sommet B) {
        for (Map.Entry<Sommet, Integer> entry : sommAdj.get(A).entrySet()) {
            if (entry.getKey().equals(B)) {
                return entry.getValue();
            }
        }
        return 0;
    }
    
    /**
     * Edita el peso de la arista entre A y B (Si existe). <p>
     * Para propósitos de este grafo, A y B son completamente intercambiables, 
     * dado que el grafo es no dirigido.
     * @param A El vértice origen de la arista
     * @param B
     * @param i 
     */
    public void setPoids(Sommet A, Sommet B, int i) {
        for (Map.Entry<Sommet, Integer> entry : sommAdj.get(A).entrySet()) {
            if (entry.getKey().equals(B)) {
                entry.setValue(i);
            }
        }
    }
    
    /**
     * Función de utilidad para hallar ciclos dentro del grafo
     * @param s El vértice actual sobre el que se trabaja
     * @param visites Un mapa que relaciona los vértices con un boleano que indica
     * si ya se visitó ese vértice
     * @param pere El vértice adyacente por el que se llegó al actual
     * @return True si se encuentra un camino para regresar al mismo vértice 
     * desde otro disinto al inicial
     */
    private boolean cycleUtil(Sommet s, Map<Sommet, Boolean> visites, Sommet pere) {
        
        visites.replace(s, Boolean.TRUE);
        
        Set<Sommet> adj = sommAdj.get(s).keySet();
        
        for (Sommet sommet : adj) {
            if(!visites.get(sommet)){
                if(cycleUtil(sommet, visites, s))
                    return true;
            }else if (sommet != pere)
                return true;
        }
        return false;
    }
    /**
     * Función que utiliza el metodo cycleUtil() en cada vértice para hallar 
     * todas las posibilidades de ciclo
     * @see com.guacha.lab2.Graphe#cycleUtil(com.guacha.lab2.Sommet, java.util.Map, com.guacha.lab2.Sommet) cycleUtil()
     * @return True si para cualquier vértice del grafo, el método cycleUtil() 
     * retorna verdadero
     */
    public boolean isCyclic() {
        Map<Sommet, Boolean> visites = new HashMap<>();
        
        for (Sommet sommet : sommAdj.keySet()) {
            visites.put(sommet, Boolean.FALSE);
        }
        
        for (Sommet sommet : sommAdj.keySet()) {
            if(!visites.get(sommet))
                if (cycleUtil(sommet, visites, null)) {
                    return true;
                }
        }
        return false;        
    }
    
    
}

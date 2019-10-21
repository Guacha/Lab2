/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guacha.lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
    
    /**
     * Mapa que relaciona los Vértices con sus conexiones, y cada conexión relaciona
     * el destino con su repsectivo peso
     */
    private final Map<Sommet, Map<Sommet, Integer>> sommAdj;
    
    /**
     * Constructor estándar para un grafo, genera un mapa vacío para las relaciones
     */
    public Graphe() {
        this.sommAdj = new HashMap<>();
    }
    
    /**
     * Getter estándar para el mapa de relaciones
     * @return El mapa de relaciones de este grafo
     */
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
     * debe ser mayor que 0
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
            sb.append("Conexiones de ").append(entry.getKey().nombre).append(": ").append("\n");
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
        return sommAdj.get(a).containsKey(b) && sommAdj.get(b).containsKey(a);
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
     * @param B El vértice destino de la arista
     * @param i El nuevo peso de la arista
     */
    public void setPoids(Sommet A, Sommet B, int i) {
        sommAdj.get(A).replace(B, i);
        sommAdj.get(B).replace(A, i);
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
        
        sommAdj.keySet().forEach((sommet) -> {
            visites.put(sommet, Boolean.FALSE);
        });
        
        if (sommAdj.keySet().stream().filter((sommet) -> (!visites.get(sommet))).anyMatch((sommet) -> (cycleUtil(sommet, visites, null)))) {
            return true;
        }
        return false;        
    }
    
    /**
     * Función boleana que retorna verdadero si el árbol es conexo<p>
     * El método realiza un recorrido desde un vértice arbitrario, y entonces 
     * compara el tamaño de la lista obtenida por el método DFS(), y si esta 
     * contiene todos los vértices, entonces el arbol es conexo
     * @return True si y solo si la lista obtenida por el método DFS() contiene todos
     * los vértices del grafo.
     * @see com.guacha.lab2.Graphe#DFS() DFS()
     */
    public boolean isConnecte() {
        return DFS().size() == sommAdj.size();
    }
    
    /**
     * Método publico que hace un recorrido de vértices por profundidad
     * @return Una lista enlzadada que contiene los vértices que se recorrieron en
     * orden.
     * @see com.guacha.lab2.Graphe#DFSUtil(com.guacha.lab2.Sommet, java.util.Map, java.util.LinkedList) DFSUtil()
     */
    public LinkedList<Sommet> DFS() {
        Map<Sommet, Boolean> m = new HashMap<>();
        sommAdj.keySet().forEach((sommet) -> {
            m.put(sommet, Boolean.FALSE);
        });
        Sommet fst = sommAdj.keySet().iterator().next();
        LinkedList<Sommet> liste = new LinkedList<>();
        return DFSUtil(fst, m, liste);
    }
    
    private LinkedList<Sommet> DFS(Sommet s) {
        Map<Sommet, Boolean> m = new HashMap<>();
        sommAdj.keySet().forEach((sommet) -> {
            m.put(sommet, Boolean.FALSE);
        });
        LinkedList<Sommet> liste = new LinkedList<>();
        return DFSUtil(s, m, liste);
    }
    
    /**
     * Función de utulidad para el DFS, recorre el grafo recursivamente y va añadiendo
     * vertices a una lista. <p>
     * Se obvian los vértices que ya se hayan visitado
     * @param actuelle El vértice actual sobre el que se trabaja
     * @param visites La lista de los vértices ya visitados (HashMap)
     * @param liste La lista resultado
     * @return La lista de vertices encontrados una vez se termina el recorrido
     */
    private LinkedList<Sommet> DFSUtil(Sommet actuelle, Map<Sommet, Boolean> visites, LinkedList<Sommet> liste) {
        visites.replace(actuelle, true);

        liste.add(actuelle);
        for (Sommet sommet : sommAdj.get(actuelle).keySet()) {
            if (!visites.get(sommet)) {
                DFSUtil(sommet, visites, liste);
            }
        }
        
        return liste;
    }
    
    /**
     * Esta función retorna un objeto de tipo grafo igual a este en el que están 
     * contenidos todos los vértices de el grafo padre, pero solo está conectado 
     * en forma de MST (Árbol de expansión mínima). <p>
     * Este método usa el algoritmo de Kruskal, por el cual, se busca de entre 
     * todas las aristas, la de menor peso, y se añade al arbol siempre y cuando 
     * esta no forme un ciclo
     * @return Un grafo que contiene el MST del grafo ingresado
     */
    public Graphe kruskalMST() {
        //Lista de aristas que se han intentado de añadir al árbol
        Map<Sommet, Map<Sommet, Boolean>> revises = new HashMap<>();                            //En esta lista se añaden las aristas que se intentaron ingresar, aunque no sean válidas para el MST
        Graphe MST = new Graphe();
        
        //Expresión para llenar la lista de visitados de false, y para añadir todos
        //los vértices del grafo al MST
        sommAdj.entrySet().stream().map((entry) -> {                                            //Expresión Lambda
            MST.addVert(entry.getKey());
            return entry;
        }).forEachOrdered((entry) -> {
            Map<Sommet, Boolean> map = new HashMap();
            entry.getValue().entrySet().stream().map((arete) -> {
                map.put(arete.getKey(), false);
                return arete;
            }).forEachOrdered((_item) -> {
                revises.put(entry.getKey(), map);
            });
        });
        
        //Se añaden aristas válidas hasta que el MST sea conexo
        while(!MST.isConnecte()){
            boolean added = false;
            Sommet sel1 = null, sel2 = null;
            int poids = Integer.MAX_VALUE;
            while(!added){
                
                //Se busca la arista de menor peso de entre todas las aristas del grafo
                for (Map.Entry<Sommet, Map<Sommet, Integer>> entry : sommAdj.entrySet()) {
                    for (Map.Entry<Sommet, Integer> ent : entry.getValue().entrySet()) {
                        if (ent.getValue() < poids && !revises.get(entry.getKey()).get(ent.getKey())) {
                            sel1 = entry.getKey();
                            sel2 = ent.getKey();
                            poids = ent.getValue();
                        }
                    }
                }
                //Si se encontró una arista válida
                if (sel1 != null) {  
                    //Se añade la arista
                    MST.addArete(sel1, sel2, poids);
                    revises.get(sel1).put(sel2, true);
                    revises.get(sel2).put(sel1, true);
                    
                    //Se verifica si es cíclico
                    if (MST.isCyclic()) {
                        //Si es cíclico, se quita la arista añadida
                        MST.quitArete(sel1, sel2);
                        poids = Integer.MAX_VALUE;
                    } else {
                        //Si no, se deja añadida
                        added = true;
                    }
                }
            }
        }
        return MST;
    }
    /**
     * Esta función retorna un objeto de tipo grafo igual a este en el que están 
     * contenidos todos los vértices de el grafo padre, pero solo está conectado 
     * en forma de MST (Árbol de expansión mínima). <p>
     * Este método utiliza el algoritmo de Prim, por el cual, se inicia en un 
     * vértice dado, y se toma la arista de menor peso que incida en este, luego 
     * se toman todos los vetices sobre los que incide la arista seleccionada, y 
     * así sucesivamente hasta tener un MST
     * @param start el vértice en el que se va a iniciar
     * @return Un grafo que contiene el MST del grafo dado
     */
    public Graphe primMST(Sommet start) {
        Graphe MST = new Graphe();
        Map<Sommet, Map<Sommet, Boolean>> revises = new HashMap<>();
        sommAdj.entrySet().forEach((entry) -> {
            MST.addVert(entry.getKey());
            Map<Sommet, Boolean> map = new HashMap<>();
            for (Map.Entry<Sommet, Integer> ent : entry.getValue().entrySet()) {
                map.put(ent.getKey(), false);   
                revises.put(entry.getKey(), map);
            }
        });       
        
        Set<Sommet> selects = new HashSet<>();
        selects.add(start);       
        
        while(!MST.isConnecte()) {
            Sommet sel1 = null, sel2 = null;
            int poids = Integer.MAX_VALUE;
            boolean added = false;
            while(!added) {
                for (Sommet select : selects) {
                    for (Map.Entry<Sommet, Integer> entry : sommAdj.get(select).entrySet()) {
                        if (entry.getValue() < poids && !revises.get(select).get(entry.getKey())) {
                            sel1 = select;
                            sel2 = entry.getKey();
                            poids = entry.getValue();
                        }
                    }
                }
                if (sel1 != null) {
                    MST.addArete(sel1, sel2, poids);
                    revises.get(sel1).put(sel2, true);
                    revises.get(sel2).put(sel1, true);
                    if (MST.isCyclic()) {
                        MST.quitArete(sel1, sel2);
                        poids = Integer.MAX_VALUE;
                    } else {
                        selects.add(sel2);
                        added = true;
                    }
                }
            }
        }
        
        return MST;
    }
    
    public Graphe Dijkstra(Sommet start, Sommet fin) {
        Graphe camino = new Graphe();
        Set<Sommet> selectione = new HashSet<>();
        Map<Sommet, ArrayList> dijkstra = new HashMap<>();
        ArrayList<Object> temp;
        for (Sommet sommet : sommAdj.keySet()) {
            temp = new ArrayList<>();
            if (sommet.equals(start)) {
                temp.add(start);
                temp.add(0);
            } else {
                temp.add(null);
                temp.add(Integer.MAX_VALUE);
            }
            dijkstra.put(sommet, temp);
        }
        selectione.add(start);
        while(!selectione.contains(fin)) {
            int poids = Integer.MAX_VALUE;
            Sommet voisin = null, ant = null;
            for (Map.Entry<Sommet, ArrayList> entry : dijkstra.entrySet()) {
                if (selectione.contains(entry.getKey())) {
                    for (Map.Entry<Sommet, Integer> arete : sommAdj.get(entry.getKey()).entrySet()) {
                        if (!selectione.contains(arete.getKey())) {
                            if (((int)entry.getValue().get(1) + arete.getValue()) < poids) {
                                poids = (int)entry.getValue().get(1) + arete.getValue();
                                voisin = arete.getKey();
                                ant = entry.getKey();
                            }
                        }
                    }
                }
            }
            if (voisin != null) {
                selectione.add(voisin);
                ArrayList aux = dijkstra.get(voisin);
                aux.remove(1);
                aux.add(poids);
                aux.remove(0);
                aux.add(0, ant);
            }
        }
        Sommet actuelle = fin, ant = (Sommet)dijkstra.get(fin).get(0);
        camino.addVert(fin);
        while (!actuelle.equals(ant)) {
            camino.addVert(ant);
            camino.addArete(ant, actuelle, getPoids(ant, actuelle));
            actuelle = ant;
            ant = (Sommet)dijkstra.get(actuelle).get(0);
        }
        return camino;
    }
    
    public LinkedList<Sommet> interpretDijkstra(Sommet start) {
        return DFS(start);
    }
    
}

package controller.tda.list.graph;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.HashMap;

import controller.tda.LabelException;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;

public class GraphLabelDirect<E> extends GraphDirect {
    protected E labels[];
    protected HashMap<E, Integer> dictVertices;
    private Class<E> clazz;

    public GraphLabelDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        labels = (E[]) Array.newInstance(clazz, nro_vertices + 1);
        dictVertices = new HashMap<>();
    }


    

    public double[][] floydWarshall() {
        int n = nro_vertices();
        double[][] dist = new double[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Double.MAX_VALUE; 
                }
            }
        }
        try {
       
            for (int i = 1; i <= n; i++) {
                LinkedList<Adycencia> adjacencies = adycencias(i); 
                for (int j = 0; j < adjacencies.getSize(); j++) { 
                    Adycencia adj = adjacencies.get(j);
                    dist[i][adj.getDestination()] = adj.getWeight(); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }

    
   


    public double[] bellmanFord(int startVertex) throws IndexOutOfBoundsException, ListEmptyException {
        int n = nro_vertices(); 
        double[] dist = new double[n + 1];
        int[] pred = new int[n + 1];


        for (int i = 1; i <= n; i++) {
            dist[i] = Double.MAX_VALUE;
            pred[i] = -1;
        }
        dist[startVertex] = 0;
  
     for (int i = 1; i <= n - 1; i++) {
        for (int u = 1; u <= n; u++) {
            LinkedList<Adycencia> adjacencies = adycencias(i);
            for (int j = 0; j < adjacencies.getSize(); j++) {
                Adycencia adj = adjacencies.get(j);
                int v = adj.getDestination();
                double weight = adj.getWeight();
                if (dist[u] != Double.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pred[v] = u;
                }
            }
        }
    }
    for (int u = 1; u <= n; u++) {
        LinkedList<Adycencia> adjacencies = adycencias(u);
        for (int j = 0; j < adjacencies.getSize(); j++) {
            Adycencia adj = adjacencies.get(j);
            int v = adj.getDestination();
            double weight = adj.getWeight();
            if (dist[u] != Double.MAX_VALUE && dist[u] + weight < dist[v]) {
                System.out.println("Graph contains a negative weight cycle");
                return null; // Negative cycle detected
            }
        }
    }

    return dist;
}

    // Fórmula Haversine para calcular la distancia en kilómetros entre dos puntos
    private static final double R = 6371; 

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; 
    }




    public Boolean is_edgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            return is_edge(getVerticeL(v1), getVerticeL(v2));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeL(E v1, E v2, Double lat1, Double lon1, Double lat2, Double lon2) throws Exception {
        if (isLabelsGraph()) {
            float distance = (float) haversine(lat1, lon1, lat2, lon2); 
            add_edge(getVerticeL(v1), getVerticeL(v2), distance); 
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            insertEdgeL(v1, v2, 0.0, 0.0, 0.0, 0.0);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public LinkedList<Adycencia> adycenciasL(E v) throws Exception {
        if (isLabelsGraph()) {
            return adycencias(getVerticeL(v));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void labelsVerticeL(Integer v, E data) {
        labels[v] = data;
        dictVertices.put(data, v);
    }

    public Boolean isLabelsGraph() {
        Boolean band = true;
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    public Integer getVerticeL(E label) {
        return dictVertices.get(label);
    }

    public E getLabelL(Integer v1) {
        return labels[v1];
    }

    @Override
    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                grafo += "V" + i + " [" + getLabelL(i).toString() + "]" + "\n";
                LinkedList<Adycencia> lista = adycencias(i);
                if (!lista.isEmpty()) {
                    Adycencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adycencia a = ady[j];
                        grafo += "ady " + "V" + a.getDestination() + " weight: " + a.getWeight() + " [" + getLabelL(a.getDestination()).toString() + "]" + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grafo;
    }

    public String drawGraph() {
        try {
        
            System.out.println("\n=== CREANDO ARISTAS DE PRUEBA ===");
           
            insertEdgeL(getLabelL(1), getLabelL(2), 40.7128, -74.0060, 34.0522, -118.2437);
            insertEdgeL(getLabelL(2), getLabelL(3), 34.0522, -118.2437, 41.8781, -87.6298); 
            insertEdgeL(getLabelL(1), getLabelL(4), 40.7128, -74.0060, 51.5074, -0.1278); 
            insertEdgeL(getLabelL(3), getLabelL(5), 41.8781, -87.6298, 37.7749, -122.4194); 
            insertEdgeL(getLabelL(4), getLabelL(6), 51.5074, -0.1278, 48.8566, 2.3522); 

            StringBuilder grafo = new StringBuilder();
            grafo.append("[");
    
            System.out.println("=== DEBUGGER GRAFO ===");
            System.out.println("Número total de vértices: " + this.nro_vertices());
    
      
            int edgeId = 1;
    
            for (int i = 1; i <= this.nro_vertices(); i++) {
                LinkedList<Adycencia> lista = adycencias(i);
                System.out.println("Revisando adyacencias del nodo " + i + ":");
    
                if (!lista.isEmpty()) {
                    Adycencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adycencia a = ady[j];
                   
                        grafo.append("{")
                             .append("\"id\": ").append(edgeId).append(", ")
                             .append("\"origin\": ").append(i).append(", ")
                             .append("\"destination\": ").append(a.getDestination())
                             .append("},\n");
    
                        edgeId++; 
                    }
                }
            }
    
            grafo.append("]");
    
       
            FileWriter jsonFile = new FileWriter("resources" + File.separatorChar + "graph" + File.separatorChar + "graph.json");
            jsonFile.write(grafo.toString());
            jsonFile.flush();
            jsonFile.close();
    
            System.out.println("\n=== ARCHIVO GUARDADO ===");
            System.out.println("Archivo guardado en: resources/graph/graph.json");
    
            return grafo.toString();
    
        } catch (Exception e) {
            System.out.println("\n=== ERROR ===");
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
}

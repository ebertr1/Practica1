package com.example.rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import controller.tda.list.graph.GraphLabelNoDirect;
import models.Sintetica;

@Path("fast")
public class fast {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        StringBuilder results = new StringBuilder();
           // Imprimir el encabezado con líneas
           results.append("+-----------------+---------------------+---------------------+----------------------+").append("\n");
           results.append("| Tamaño del Grafo | Bellman-Ford (Tiempo) | Floyd-Warshall (Tiempo) | Algoritmo Más Rápido |").append("\n");
           results.append("+-----------------+---------------------+---------------------+----------------------+").append("\n");
   
        // Definir los tamaños de los grafos a probar
        int[] sizes = {10, 20, 30};

        for (int size : sizes) {
            try {
                // Crear el grafo con el tamaño actual
                GraphLabelNoDirect<Sintetica> graph = new GraphLabelNoDirect<>(size, Sintetica.class);
                Sintetica[] m = new Sintetica[size];

                // Simular los vértices y agregar etiquetas
                for (int i = 0; i < size; i++) {
                    Sintetica s = new Sintetica(); // Puedes personalizar este objeto
                    m[i] = s;
                    graph.labelsVerticeL(i + 1, s);
                }

                // Agregar aristas de ejemplo (puedes modificar esta parte para mayor aleatoriedad)
                for (int i = 0; i < size - 1; i++) {
                    graph.insertEdgeL(m[i], m[i + 1], (i + 1) * 2.0f);
                }

                // Ejecutar y medir el tiempo de Bellman-Ford
                System.out.println("Ejecutando Bellman-Ford para " + size + " vértices...");
                long startTime = System.nanoTime();
                graph.bellmanFord(1);  // Usando el vértice 1 como origen
                long endTime = System.nanoTime();
                long bellmanFordDuration = endTime - startTime;

                // Ejecutar y medir el tiempo de Floyd-Warshall
                System.out.println("Ejecutando Floyd-Warshall para " + size + " vértices...");
                startTime = System.nanoTime();
                graph.floydWarshall();  // Todos los pares de vértices
                endTime = System.nanoTime();
                long floydWarshallDuration = endTime - startTime;

                // Determinar el algoritmo más rápido
                String fastestAlgorithm = bellmanFordDuration < floydWarshallDuration ? "Bellman-Ford" : "Floyd-Warshall";

                // Formatear los resultados con alineación
                results.append(String.format("| %-15s | %-19d | %-19d | %-19s |\n", 
                        size + " vértices", bellmanFordDuration, floydWarshallDuration, fastestAlgorithm));

            } catch (Exception e) {
                results.append("| Error en ejecución | Error en ejecución  | Error en ejecución  | Error en ejecución  |\n");
            }
        }

        // Imprimir la línea final
        results.append("+-----------------+---------------------+---------------------+----------------------+").append("\n");

        return results.toString();
    }
}

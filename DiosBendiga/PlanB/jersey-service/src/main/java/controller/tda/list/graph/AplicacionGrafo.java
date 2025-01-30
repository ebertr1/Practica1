package controller.tda.list.graph;

import java.util.Scanner;

public class AplicacionGrafo {

    // Radio de la Tierra en kilómetros
    private static final double RADIO_TIERRA = 6371.0;

    // Método para calcular la distancia entre dos puntos utilizando la fórmula de Haversine
    public static double distanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        // Convertir de grados a radianes
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        // Diferencias de latitudes y longitudes
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        // Fórmula de Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros
        return RADIO_TIERRA * c;
    }

    // Algoritmo de Floyd-Warshall
    public static double[][] floydWarshall(double[][] dist) {
        int V = dist.length;
        double[][] distCopia = new double[V][V];

        // Copiar la matriz de distancias original
        for (int i = 0; i < V; i++) {
            System.arraycopy(dist[i], 0, distCopia[i], 0, V);
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (distCopia[i][k] + distCopia[k][j] < distCopia[i][j]) {
                        distCopia[i][j] = distCopia[i][k] + distCopia[k][j];
                    }
                }
            }
        }
        return distCopia;
    }

    // Algoritmo de Bellman-Ford
    public static double[] bellmanFord(double[][] dist, int verticeInicio) {
        int V = dist.length;
        double[] distancias = new double[V];
        for (int i = 0; i < V; i++) {
            distancias[i] = Double.MAX_VALUE;
        }
        distancias[verticeInicio] = 0;

        // Relajación de todas las aristas V-1 veces
        for (int i = 0; i < V - 1; i++) {
            for (int u = 0; u < V; u++) {
                for (int v = 0; v < V; v++) {
                    if (dist[u][v] != Double.MAX_VALUE && distancias[u] != Double.MAX_VALUE &&
                        distancias[u] + dist[u][v] < distancias[v]) {
                        distancias[v] = distancias[u] + dist[u][v];
                    }
                }
            }
        }
        return distancias;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int vertices = 6;
        double[][] grafo = new double[vertices][vertices];

        // Inicializar distancias con valores infinitos
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (i == j) {
                    grafo[i][j] = 0;
                } else {
                    grafo[i][j] = Double.MAX_VALUE;
                }
            }
        }

        // Distancias entre los vértices (en kilómetros)
        grafo[0][1] = grafo[1][0] = distanciaHaversine(40.7128, -74.0060, 34.0522, -118.2437);  // Calva <-> Solana
        grafo[1][2] = grafo[2][1] = distanciaHaversine(34.0522, -118.2437, 41.8781, -87.6298);  // Solana <-> Pradera
        grafo[0][3] = grafo[3][0] = distanciaHaversine(40.7128, -74.0060, 51.5074, -0.1278);    // Calva <-> Horizonte
        grafo[2][4] = grafo[4][2] = distanciaHaversine(41.8781, -87.6298, 37.7749, -122.4194);  // Pradera <-> Aurora
        grafo[3][5] = grafo[5][3] = distanciaHaversine(51.5074, -0.1278, 48.8566, 2.3522);    // Horizonte <-> Renacer

        String[] ubicaciones = {"Calva", "Solana", "Pradera", "Horizonte", "Aurora", "Renacer"};

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Mostrar Grafo");
            System.out.println("2. Calcular Distancias (Floyd)");
            System.out.println("3. Calcular Distancias (Bellman)");
            System.out.println("4. Calcular Camino más Corto de Pradera a Aurora");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Mostrar grafo
                    System.out.println("Grafo (Distancias):");
                    for (int i = 0; i < vertices; i++) {
                        for (int j = 0; j < vertices; j++) {
                            if (grafo[i][j] == Double.MAX_VALUE) {
                                System.out.print("INF ");
                            } else {
                                System.out.print(grafo[i][j] + " ");
                            }
                        }
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.print("Introduce el vértice de inicio (0 - Calva): ");
                    int verticeInicio = scanner.nextInt();
                    double[][] distanciasFW = floydWarshall(grafo);
                    System.out.println("Resultados de Floyd-Warshall:");
                    for (int i = 0; i < distanciasFW.length; i++) {
                        for (int j = 0; j < distanciasFW[i].length; j++) {
                            if (distanciasFW[i][j] == Double.MAX_VALUE) {
                                System.out.println(ubicaciones[j] + ": INF");
                            } else {
                                System.out.println(ubicaciones[j] + ": " + distanciasFW[i][j]);
                            }
                        }
                        System.out.println();
                    }
                    break;
                case 3:
                    // Calcular usando Bellman-Ford
                    System.out.print("Introduce el vértice de inicio (0 - Calva): ");
                    int verticeInicioBF = scanner.nextInt();
                    double[] distanciasBF = bellmanFord(grafo, verticeInicioBF);
                    System.out.println("Resultados de Bellman-Ford:");
                    for (int i = 0; i < distanciasBF.length; i++) {
                        if (distanciasBF[i] == Double.MAX_VALUE) {
                            System.out.println(ubicaciones[i] + ": INF");
                        } else {
                            System.out.println(ubicaciones[i] + ": " + distanciasBF[i]);
                        }
                    }
                    break;
                case 4:
                    // Camino más corto desde Pradera (índice 2) a Aurora (índice 4)
                    System.out.println("Camino más corto de Pradera a Aurora:");
                    double caminoMasCorto = grafo[2][4];
                    if (caminoMasCorto == Double.MAX_VALUE) {
                        System.out.println("No existe un camino.");
                    } else {
                        System.out.println("Distancia de Pradera a Aurora: " + caminoMasCorto);
                    }
                    break;
                case 5:
                    // Salir
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}

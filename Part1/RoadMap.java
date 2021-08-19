
/* Put your student number here
 *1935938
 * Optionally, if you have any comments regarding your submission, put them here.
 * For instance, specify here if your program does not generate the proper output or does not do it in the correct manner.
 */

import java.util.*;
import java.io.*;

class Vertex {

    // Constructor: set name, chargingStation and index according to given values,
    // initilaize incidentRoads as empty array
    public Vertex(String placeName, boolean chargingStationAvailable, int idx) {
        name = placeName;
        incidentRoads = new ArrayList<Edge>();
        index = idx;
        chargingStation = chargingStationAvailable;
        visited = false;
    }

    public String getName() {
        return name;
    }

    public boolean hasChargingStation() {
        return chargingStation;
    }

    public ArrayList<Edge> getIncidentRoads() {
        return incidentRoads;
    }

    // Add a road to the array incidentRoads
    public void addIncidentRoad(Edge road) {
        incidentRoads.add(road);
    }

    public int getIndex() {
        return index;
    }

    public boolean isVisited() {
        return visited;
    }
    public void visit() {
        visited = true;
    }
    public  LinkedList<Vertex> getAdjacentVertex() {
        LinkedList<Vertex> adjacentVertex = new LinkedList<>();
        ArrayList<Edge> roads = getIncidentRoads();
        System.out.println(name + " has roads: " + roads.size());
        for (Edge road : roads) {
            adjacentVertex.add(road.getFirstVertex());
            adjacentVertex.add(road.getSecondVertex());
        }
        return adjacentVertex;
    }

    public String toString() {
        return this.getName() + (this.hasChargingStation() ? "*" : "");
    }

    private String name; // Name of the place
    private ArrayList<Edge> incidentRoads; // Incident edges
    private boolean chargingStation; // Availability of charging station
    private int index; // Index of this vertex in the vertex array of the map
    private boolean visited;
}

class Edge implements Comparable<Edge> {
    public Edge(int roadLength, Vertex firstPlace, Vertex secondPlace) {
        length = roadLength;
        incidentPlaces = new Vertex[] { firstPlace, secondPlace };
    }

    public Vertex getFirstVertex() {
        return incidentPlaces[0];
    }

    public Vertex getSecondVertex() {
        return incidentPlaces[1];
    }

    public int getLength() {
        return length;
    }

    private int length;
    private Vertex[] incidentPlaces;

    @Override
    public int compareTo(Edge u) {
        //return (getLength() - u.getLength());
        return (u.getLength() - getLength());
    }
}

// A class that represents a sparse matrix
public class RoadMap {

    // Default constructor
    public RoadMap() {
        places = new ArrayList<Vertex>();
        roads = new ArrayList<Edge>();
        //stores the vertex while the graph is being traversed
        paths = new LinkedList<Vertex>();
    }

    // Auxiliary function that prints out the command syntax
    public static void printCommandError() {
        System.err.println("ERROR: use one of the following commands");
        System.err.println(" - Read a map and print information: java RoadMap -i <MapFile>");
        System.err.println(
                " - Read a map and find shortest path between two vertices with charging stations: java RoadMap -s <MapFile> <StartVertexIndex> <EndVertexIndex>");
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 2 && args[0].equals("-i")) {
            RoadMap map = new RoadMap();
            try {
                map.loadMap(args[1]);
            } catch (Exception e) {
                System.err.println("Error in reading map file");
                System.exit(-1);
            }

            System.out.println("Read road map from " + args[1] + ":");
            map.printMap();
        } else if (args.length == 4 && args[0].equals("-s")) {
            RoadMap map = new RoadMap();
            map.loadMap(args[1]);
            System.out.println("Read road map from " + args[1] + ":");
            map.printMap();

            int startVertexIdx = -1, endVertexIdx = -1;
            try {
                startVertexIdx = Integer.parseInt(args[2]);
                endVertexIdx = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                System.err.println("Error: start vertex and end vertex must be specified using their indices");
                System.exit(-1);
            }

            if (startVertexIdx < 0 || startVertexIdx >= map.numPlaces()) {
                System.err.println("Error: invalid index for start vertex");
                System.exit(-1);
            }

            if (endVertexIdx < 0 || endVertexIdx >= map.numPlaces()) {
                System.err.println("Error: invalid index for end vertex");
                System.exit(-1);
            }

            System.out.println();

            Vertex startVertex = map.getPlace(startVertexIdx);
            Vertex endVertex = map.getPlace(endVertexIdx);
            if (map.isConnectedWithChargingStations(startVertex, endVertex)) {
                System.out.println("There is at least one path connecting " + map.getPlace(startVertexIdx).getName() + " and "
                        + map.getPlace(endVertexIdx).getName() + " with charging stations");
            } else {
                System.out.println("There is no path connecting " + map.getPlace(startVertexIdx).getName() + " and "
                        + map.getPlace(endVertexIdx).getName() + " with charging stations");
            }

        } else {
            printCommandError();
            System.exit(-1);
        }
    }

    // Load matrix entries from a text file
    public void loadMap(String filename) {
        File file = new File(filename);
        places.clear();
        roads.clear();

        try {
            Scanner sc = new Scanner(file);

            // Read the first line: number of vertices and number of edges
            int numVertices = sc.nextInt();
            int numEdges = sc.nextInt();

            for (int i = 0; i < numVertices; ++i) {
                // Read the vertex name and its charing station flag
                String placeName = sc.next();
                int chargingStationFlag = sc.nextInt();
                boolean hasChargingStataion = (chargingStationFlag == 1);

                // Add your code here to create a new vertex using the information above and add
                // it to places
                places.add(new Vertex(placeName, hasChargingStataion, i));
            }

            for (int j = 0; j < numEdges; ++j) {
                // Read the edge length and the indices for its two vertices
                int vtxIndex1 = sc.nextInt();
                int vtxIndex2 = sc.nextInt();
                int length = sc.nextInt();
                Vertex vtx1 = places.get(vtxIndex1);
                Vertex vtx2 = places.get(vtxIndex2);

                // Add your code here to create a new edge using the information above and add
                // it to roads
                // You should also set up incidentRoads for each vertex
                roads.add(new Edge(length, vtx1, vtx2));
            }

            sc.close();

            // Add your code here if approparite
        } catch (Exception e) {
            e.printStackTrace();
            places.clear();
            roads.clear();
        }
    }


    // Check if two vertices are connected by a path with charging stations on each intermedia vertex.
    // Return true if such a path exists; return false otherwise.
    // The worst-case time complexity of your algorithm should be no worse than O(v + e),
    // where v and e are the number of vertices and the number of edges in the graph.
    public boolean isConnectedWithChargingStations(Vertex startVertex, Vertex endVertex) {
        // Sanity check
        if (startVertex.getIndex() == endVertex.getIndex()) {
            return true;
        }

        // Add your code here
        return vertexTraversalBFS(startVertex, endVertex);
    }

    //Traverse through the vertex (Breath First Serach)
    public boolean vertexTraversalBFS(Vertex initialVertex, Vertex destinVertex) {

        // Just to handle receiving an uninitialized vertex, otherwise an
        // exception will be thrown when we try to add it to queue
        if (initialVertex == null) {
            System.out.print("Start vertex not found");
            return false;
        }
        if (destinVertex == null) {
            System.out.print("Destination vertex not found");
            return false;
        }

        // Creating the queue, and adding the first vertex (step 1)
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.add(initialVertex);

        while (!queue.isEmpty()) {
            Vertex currentFirst = queue.removeFirst();

            // In some cases, you must add a particular vertex more than once before
            // actually visiting that vertex, making sure to check and skip that vertex if it have
            // been encountered before
            if (currentFirst.isVisited())
                continue;

            // Mark the vertex as visited
            currentFirst.visit();
            paths.add(currentFirst);

            LinkedList<Vertex> allNeighbours = getAdjacentVertex(currentFirst);
            System.out.println(currentFirst.getName() + " has " + allNeighbours.size() + " adjacent vertex");

            // check whether the list of neighbours is null before proceeding, otherwise
            // the for-each loop will throw an exception
            if (allNeighbours.size() == 0) {
                paths.removeLast();
                System.out.println(currentFirst.getName() + " has no adjacent vertices, path unusable");
                continue;
            }

            if (!currentFirst.equals(initialVertex) && !currentFirst.hasChargingStation()) {
                paths.removeLast();
                System.out.println(currentFirst.getName() + " does not have a charging station, path unsuitable");
                continue;
            }

            // Check if any adjacent vertex is the end vertex
            for (Vertex v : allNeighbours) {
                // reached destination
                if (v.equals(destinVertex)) {
                    paths.add(destinVertex);
                    System.out.println(v.getName() + " destination reached");
                    System.out.println("path: " + paths);
                    return true;
                }
            }

            for (Vertex neighbour : allNeighbours) {
                // We only add unvisited neighbours
                if (!neighbour.isVisited()) {
                    queue.add(neighbour);
                }
            }
        }
        return false;
    }

    public void printMap() {
        System.out.println("The map contains " + this.numPlaces() + " places and " + this.numRoads() + " roads");
        System.out.println();

        System.out.println("Places:");

        for (Vertex v : places) {
            System.out.println("- name: " + v.getName() + ", charging station: " + v.hasChargingStation());
        }

        System.out.println();
        System.out.println("Roads:");

        for (Edge e : roads) {
            System.out.println("- (" + e.getFirstVertex().getName() + ", " + e.getSecondVertex().getName()
                    + "), length: " + e.getLength());
        }
    }

    public void printPath(ArrayList<Vertex> path) {
        System.out.print("(  ");

        for (Vertex v : path) {
            System.out.print(v.getName() + "  ");
        }

        System.out.println(")");
    }

    public int numPlaces() {
        return places.size();
    }

    public int numRoads() {
        return roads.size();
    }

    public Vertex getPlace(int idx) {
        return places.get(idx);
    }

    public  LinkedList<Vertex> getAdjacentVertex(Vertex vertex) {
        LinkedList<Vertex> adjacentVertex = new LinkedList<>();
        //order the roads by the shortest length first so shortest path is searched first
        Collections.sort(roads);
        for (Edge road : roads) {
            if (road.getFirstVertex().equals(vertex)) {
                adjacentVertex.add(road.getSecondVertex());
            }
        }
        return adjacentVertex;
    }

    private ArrayList<Vertex> places;
    private ArrayList<Edge> roads;
    private LinkedList<Vertex> paths;
}

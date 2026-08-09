package edu.ug.smartdelivery.datastructure.graph;

public interface CustomGraph {
    int vertexCount();

    int edgeCount();

    void addVertex(GraphVertex vertex);

    void addEdge(int fromLocationId, int toLocationId, double weight, boolean bidirectional);

    boolean containsVertex(int locationId);

    GraphVertex getVertex(int locationId);

    GraphEdge[] neighborsOf(int locationId);

    GraphVertex[] vertices();

    GraphEdge[] edges();
}

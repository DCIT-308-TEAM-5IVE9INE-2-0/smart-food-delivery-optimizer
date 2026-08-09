package edu.ug.smartdelivery.datastructure.graph;

public class AdjacencyMatrixGraph implements CustomGraph {
    private static final int DEFAULT_CAPACITY = 16;
    private GraphVertex[] vertices;
    private double[][] weights;
    private boolean[][] hasEdge;
    private int vertexCount;
    private int edgeCount;

    public AdjacencyMatrixGraph() {
        vertices = new GraphVertex[DEFAULT_CAPACITY];
        weights = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        hasEdge = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }

    @Override
    public int vertexCount() {
        return vertexCount;
    }

    @Override
    public int edgeCount() {
        return edgeCount;
    }

    @Override
    public void addVertex(GraphVertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("vertex cannot be null");
        }
        if (containsVertex(vertex.locationId())) {
            return;
        }
        ensureCapacity(vertexCount + 1);
        vertices[vertexCount] = vertex;
        vertexCount++;
    }

    @Override
    public void addEdge(int fromLocationId, int toLocationId, double weight, boolean bidirectional) {
        int fromIndex = indexOf(fromLocationId);
        int toIndex = indexOf(toLocationId);
        if (fromIndex < 0 || toIndex < 0) {
            throw new IllegalArgumentException("both vertices must exist before adding an edge");
        }
        addDirectedEdge(fromIndex, toIndex, weight);
        if (bidirectional) {
            addDirectedEdge(toIndex, fromIndex, weight);
        }
    }

    @Override
    public boolean containsVertex(int locationId) {
        return indexOf(locationId) >= 0;
    }

    @Override
    public GraphVertex getVertex(int locationId) {
        int index = indexOf(locationId);
        if (index < 0) {
            throw new IllegalArgumentException("vertex not found: " + locationId);
        }
        return vertices[index];
    }

    @Override
    public GraphEdge[] neighborsOf(int locationId) {
        int fromIndex = indexOf(locationId);
        if (fromIndex < 0) {
            throw new IllegalArgumentException("vertex not found: " + locationId);
        }
        int count = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (hasEdge[fromIndex][i]) {
                count++;
            }
        }
        GraphEdge[] neighbors = new GraphEdge[count];
        int position = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (hasEdge[fromIndex][i]) {
                neighbors[position++] = new GraphEdge(locationId, vertices[i].locationId(), weights[fromIndex][i]);
            }
        }
        return neighbors;
    }

    @Override
    public GraphVertex[] vertices() {
        GraphVertex[] copy = new GraphVertex[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            copy[i] = vertices[i];
        }
        return copy;
    }

    @Override
    public GraphEdge[] edges() {
        GraphEdge[] allEdges = new GraphEdge[edgeCount];
        int position = 0;
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (hasEdge[i][j]) {
                    allEdges[position++] = new GraphEdge(vertices[i].locationId(), vertices[j].locationId(), weights[i][j]);
                }
            }
        }
        return allEdges;
    }

    public double weightBetween(int fromLocationId, int toLocationId) {
        int fromIndex = indexOf(fromLocationId);
        int toIndex = indexOf(toLocationId);
        if (fromIndex < 0 || toIndex < 0 || !hasEdge[fromIndex][toIndex]) {
            throw new IllegalArgumentException("edge not found");
        }
        return weights[fromIndex][toIndex];
    }

    public int indexOf(int locationId) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].locationId() == locationId) {
                return i;
            }
        }
        return -1;
    }

    private void addDirectedEdge(int fromIndex, int toIndex, double weight) {
        if (!hasEdge[fromIndex][toIndex]) {
            edgeCount++;
        }
        hasEdge[fromIndex][toIndex] = true;
        weights[fromIndex][toIndex] = weight;
    }

    private void ensureCapacity(int neededCapacity) {
        if (neededCapacity <= vertices.length) {
            return;
        }
        int newCapacity = vertices.length * 2;
        GraphVertex[] resizedVertices = new GraphVertex[newCapacity];
        double[][] resizedWeights = new double[newCapacity][newCapacity];
        boolean[][] resizedEdges = new boolean[newCapacity][newCapacity];
        for (int i = 0; i < vertexCount; i++) {
            resizedVertices[i] = vertices[i];
            for (int j = 0; j < vertexCount; j++) {
                resizedWeights[i][j] = weights[i][j];
                resizedEdges[i][j] = hasEdge[i][j];
            }
        }
        vertices = resizedVertices;
        weights = resizedWeights;
        hasEdge = resizedEdges;
    }
}

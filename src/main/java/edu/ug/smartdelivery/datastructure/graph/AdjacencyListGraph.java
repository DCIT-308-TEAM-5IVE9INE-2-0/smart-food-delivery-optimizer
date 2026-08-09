package edu.ug.smartdelivery.datastructure.graph;

public class AdjacencyListGraph implements CustomGraph {
    private static final int DEFAULT_CAPACITY = 16;
    private GraphVertex[] vertices;
    private EdgeNode[] adjacency;
    private int vertexCount;
    private int edgeCount;

    public AdjacencyListGraph() {
        vertices = new GraphVertex[DEFAULT_CAPACITY];
        adjacency = new EdgeNode[DEFAULT_CAPACITY];
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
        ensureVertexCapacity(vertexCount + 1);
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
        addDirectedEdge(fromIndex, fromLocationId, toLocationId, weight);
        if (bidirectional) {
            addDirectedEdge(toIndex, toLocationId, fromLocationId, weight);
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
        int index = indexOf(locationId);
        if (index < 0) {
            throw new IllegalArgumentException("vertex not found: " + locationId);
        }
        int count = 0;
        EdgeNode current = adjacency[index];
        while (current != null) {
            count++;
            current = current.next;
        }
        GraphEdge[] neighbors = new GraphEdge[count];
        current = adjacency[index];
        int i = 0;
        while (current != null) {
            neighbors[i++] = current.edge;
            current = current.next;
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
            EdgeNode current = adjacency[i];
            while (current != null) {
                allEdges[position++] = current.edge;
                current = current.next;
            }
        }
        return allEdges;
    }

    public int indexOf(int locationId) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].locationId() == locationId) {
                return i;
            }
        }
        return -1;
    }

    private void addDirectedEdge(int fromIndex, int fromLocationId, int toLocationId, double weight) {
        GraphEdge edge = new GraphEdge(fromLocationId, toLocationId, weight);
        adjacency[fromIndex] = new EdgeNode(edge, adjacency[fromIndex]);
        edgeCount++;
    }

    private void ensureVertexCapacity(int neededCapacity) {
        if (neededCapacity <= vertices.length) {
            return;
        }
        int newCapacity = vertices.length * 2;
        GraphVertex[] resizedVertices = new GraphVertex[newCapacity];
        EdgeNode[] resizedAdjacency = new EdgeNode[newCapacity];
        for (int i = 0; i < vertexCount; i++) {
            resizedVertices[i] = vertices[i];
            resizedAdjacency[i] = adjacency[i];
        }
        vertices = resizedVertices;
        adjacency = resizedAdjacency;
    }

    private static final class EdgeNode {
        private final GraphEdge edge;
        private final EdgeNode next;

        private EdgeNode(GraphEdge edge, EdgeNode next) {
            this.edge = edge;
            this.next = next;
        }
    }
}
